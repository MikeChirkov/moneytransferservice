package ru.netology.moneytransferservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferRequestDto {

    private String cardFromNumber;

    private String cardFromValidTill;

    private String cardFromCVV;

    private String cardToNumber;

    private Amount amount;

}
