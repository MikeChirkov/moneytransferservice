package ru.netology.moneytransferservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.moneytransferservice.exception.ConfirmationException;
import ru.netology.moneytransferservice.exception.InputDataException;
import ru.netology.moneytransferservice.exception.TransferException;
import ru.netology.moneytransferservice.model.ErrorResponse;

import java.util.concurrent.atomic.AtomicInteger;

@RestControllerAdvice
public class MoneyTransferControllerAdvice {
    private final AtomicInteger id = new AtomicInteger(0);

    @ExceptionHandler(InputDataException.class)
    public ResponseEntity<ErrorResponse> handleInputData(InputDataException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(), id.incrementAndGet()));
    }

    @ExceptionHandler(TransferException.class)
    public ResponseEntity<ErrorResponse> handleTransferError(TransferException e) {

        return ResponseEntity.internalServerError().body(new ErrorResponse(e.getMessage(), id.incrementAndGet()));
    }

    @ExceptionHandler(ConfirmationException.class)
    public ResponseEntity<ErrorResponse> handleConfirmationError(ConfirmationException e) {
        return ResponseEntity.internalServerError().body(new ErrorResponse(e.getMessage(), id.incrementAndGet()));
    }
}
