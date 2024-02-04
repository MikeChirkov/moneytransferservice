package ru.netology.moneytransferservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.exception.ConfirmationException;
import ru.netology.moneytransferservice.exception.InputDataException;
import ru.netology.moneytransferservice.exception.TransferException;
import ru.netology.moneytransferservice.model.*;
import ru.netology.moneytransferservice.repository.CardInMemoryRepository;
import ru.netology.moneytransferservice.repository.OperationInMemoryRepository;
import ru.netology.moneytransferservice.utils.Logger;

import static ru.netology.moneytransferservice.utils.Constant.COMMISSION_VALUE;

@AllArgsConstructor
@Service
public class MoneyTransferService {
    private final Logger log = Logger.getInstance();
    private final CardInMemoryRepository cardRepository;
    private final OperationInMemoryRepository operationRepository;

    public TransferResponseDto transferMoney(TransferRequestDto request) {
        var cardFrom = cardRepository.getCardByNumber(request.getCardFromNumber());
        var cardTo = cardRepository.getCardByNumber(request.getCardToNumber());

        validationCards(cardFrom, cardTo, request, request.getCardFromNumber(), request.getCardToNumber());

        var id = operationRepository.createOperation(cardFrom, cardTo, request.getAmount());
        return new TransferResponseDto(id);
    }

    public TransferResponseDto confirmOperation(ConfirmRequestDto request) {
        var operation = operationRepository.getOperationById(request.getOperationId());

        validationOperation(operation, request.getCode());

        var cardFrom = operation.getCardFrom();
        var cardTo = operation.getCardTo();
        var amount = operation.getAmount();
        var commission = amount.getValue() * COMMISSION_VALUE;

        cardTo.setAmount(Amount.builder()
                .currency(amount.getCurrency())
                .value(cardTo.getAmount().getValue() + amount.getValue())
                .build());

        cardFrom.setAmount(Amount.builder()
                .currency(amount.getCurrency())
                .value(cardFrom.getAmount().getValue() - amount.getValue() - (int) commission)
                .build());

        cardRepository.saveCard(cardTo);
        cardRepository.saveCard(cardFrom);
        operationRepository.removeOperationById(operation.getId());

        writeSuccessToLog(cardFrom, cardTo, amount);

        return new TransferResponseDto(operation.getId());
    }

    private void validationCards(Card cardFrom, Card cardTo, TransferRequestDto request,
                                 String cardNumFromRequest, String cardNumToRequest) {
        if (cardFrom == null) {
            throwInputDataException(Card.builder().cardNumber(cardNumFromRequest).build(),
                    cardTo, request.getAmount(),
                    "Карта отправителя не найдена.");
            return;
        }
        if (cardTo == null) {
            throwInputDataException(cardFrom, Card.builder().cardNumber(cardNumToRequest).build(),
                    request.getAmount(),
                    "Карта получателя не найдена.");
            return;
        }
        if (cardNumFromRequest.equals(cardNumToRequest)) {
            throwInputDataException(cardFrom, cardTo, request.getAmount(),
                    "Номера карты отправителя и карты получателя не могут быть равны.");
        }
        if (!cardFrom.getValidTill().equals(request.getCardFromValidTill())) {
            throwInputDataException(cardFrom, cardTo, request.getAmount(),
                    "Срок действия карты отправителя введен неверно.");
        }
        if (!cardFrom.getCvv().equals(request.getCardFromCVV())) {
            throwInputDataException(cardFrom, cardTo, request.getAmount(),
                    "CVV код карты отправителя введен неверно.");
        }
        if (cardFrom.getAmount().getValue() < (request.getAmount().getValue() + (request.getAmount().getValue() * COMMISSION_VALUE))) {
            throwTransferException(cardFrom, cardTo, request.getAmount(),
                    "На карте отправителя недостаточно средств для перевода.");
        }
    }

    private void validationOperation(Operation operation, String code) {
        if (operation == null) {
            throwInputDataException("Операция не найдена.");
            return;
        }
        if (!operation.getCode().equals(code)) {
            throwConfirmationException(operation.getCardFrom(), operation.getCardTo(), operation.getAmount(),
                    "Код подтверждения операции неверен.");
        }
    }

    private void throwConfirmationException(Card cardFrom, Card cardTo, Amount amount, String error) {
        writeFailToLog(cardFrom, cardTo, amount, error);
        throw new ConfirmationException(error);
    }

    private void throwTransferException(Card cardFrom, Card cardTo, Amount amount, String error) {
        writeFailToLog(cardFrom, cardTo, amount, error);
        throw new TransferException(error);
    }

    private void throwInputDataException(Card cardFrom, Card cardTo, Amount amount, String error) {
        writeFailToLog(cardFrom, cardTo, amount, error);
        throw new InputDataException(error);
    }

    private void throwInputDataException(String error) {
        writeFailToLog(error);
        throw new InputDataException(error);
    }

    private void writeFailToLog(Card cardFrom, Card cardTo, Amount amount, String error) {
        log.write(String.format("[%s] --> [%s] [Сумма: %d] [Комиссия: %d] [ОТКЛОНЕН] [%s]",
                cardFrom.getCardNumber(),
                cardTo.getCardNumber(),
                amount.getValue(),
                (int) (amount.getValue() * COMMISSION_VALUE),
                error));
    }

    private void writeFailToLog(String error) {
        log.write(String.format("[ОТКЛОНЕН] [%s]",
                error));
    }

    private void writeSuccessToLog(Card cardFrom, Card cardTo, Amount amount) {
        log.write(String.format("[%s] --> [%s] [Сумма: %d] [Комиссия: %d] [УСПЕШНО]",
                cardFrom.getCardNumber(),
                cardTo.getCardNumber(),
                amount.getValue(),
                (int) (amount.getValue() * COMMISSION_VALUE)));
    }
}
