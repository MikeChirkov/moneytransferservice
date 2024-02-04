package ru.netology.moneytransferservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InputDataException extends RuntimeException {
    public InputDataException(String message) {
        super(message);
    }
}
