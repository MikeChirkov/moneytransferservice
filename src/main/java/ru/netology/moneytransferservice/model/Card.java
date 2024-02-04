package ru.netology.moneytransferservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card {

    private String cardNumber;

    private String validTill;

    private String cvv;

    private Amount amount;

}
