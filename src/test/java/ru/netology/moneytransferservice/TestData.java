package ru.netology.moneytransferservice;

import ru.netology.moneytransferservice.model.Amount;
import ru.netology.moneytransferservice.model.ConfirmRequestDto;
import ru.netology.moneytransferservice.model.TransferRequestDto;

public class TestData {

    public static TransferRequestDto getSuccessTransferRequestDto(){
        return TransferRequestDto.builder()
                .cardToNumber("2222222222222222")
                .cardFromNumber("1111111111111111")
                .cardFromCVV("111")
                .cardFromValidTill("01/25")
                .amount(Amount.builder()
                        .value(100)
                        .currency("RUR")
                        .build())
                .build();
    }

    public static ConfirmRequestDto getSuccessConfirmRequestDto(){
        return ConfirmRequestDto.builder()
                .code("0000")
                .operationId(1)
                .build();
    }

    public static TransferRequestDto getFailedAmountTransferRequestDto(){
        return TransferRequestDto.builder()
                .cardToNumber("2222222222222222")
                .cardFromNumber("1111111111111111")
                .cardFromCVV("111")
                .cardFromValidTill("01/25")
                .amount(Amount.builder()
                        .value(10_000)
                        .currency("RUR")
                        .build())
                .build();
    }

    public static TransferRequestDto getFailedCardFromTransferRequestDto(){
        return TransferRequestDto.builder()
                .cardToNumber("2222222222222222")
                .cardFromNumber("3333333333333333")
                .cardFromCVV("111")
                .cardFromValidTill("01/25")
                .amount(Amount.builder()
                        .value(100)
                        .currency("RUR")
                        .build())
                .build();
    }

    public static TransferRequestDto getFailedCVVTransferRequestDto(){
        return TransferRequestDto.builder()
                .cardToNumber("2222222222222222")
                .cardFromNumber("1111111111111111")
                .cardFromCVV("222")
                .cardFromValidTill("01/25")
                .amount(Amount.builder()
                        .value(100)
                        .currency("RUR")
                        .build())
                .build();
    }
}
