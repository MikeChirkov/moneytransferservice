package ru.netology.moneytransferservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.netology.moneytransferservice.TestData;
import ru.netology.moneytransferservice.exception.InputDataException;
import ru.netology.moneytransferservice.exception.TransferException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class MoneyTransferServiceTest {

    @Autowired
    MoneyTransferService service;

    @Test
    void successTransferMoney() {
        var expected1 = service.transferMoney(TestData.getSuccessTransferRequestDto());
        assertEquals(expected1.getOperationId(), 1);
        var expected2 = service.confirmOperation(TestData.getSuccessConfirmRequestDto());
        assertEquals(expected2.getOperationId(), 1);
    }

    @Test
    void failedTransferMoney_1() {
        assertThrows(InputDataException.class,
                () -> service.transferMoney(TestData.getFailedCardFromTransferRequestDto()),
                "Карта отправителя не найдена.");
    }

    @Test
    void failedTransferMoney_2() {
        assertThrows(InputDataException.class,
                () -> service.transferMoney(TestData.getFailedCVVTransferRequestDto()),
                "CVV код карты отправителя введен неверно.");
    }

    @Test
    void failedTransferMoney_3() {
        assertThrows(TransferException.class,
                () -> service.transferMoney(TestData.getFailedAmountTransferRequestDto()),
                "На карте отправителя недостаточно средств для перевода.");
    }

    @Test
    void failedConfirmOperation_1() {
        assertThrows(InputDataException.class,
                () -> service.confirmOperation(TestData.getSuccessConfirmRequestDto()),
                "Операция не найдена.");
    }

}