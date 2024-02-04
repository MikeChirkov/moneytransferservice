package ru.netology.moneytransferservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfirmRequestDto {

    private String code;

    private int operationId;

}
