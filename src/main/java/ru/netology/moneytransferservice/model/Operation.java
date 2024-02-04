package ru.netology.moneytransferservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Operation {

    private int id;

    private Card cardFrom;

    private Card cardTo;

    private Amount amount;

    private String code;
}
