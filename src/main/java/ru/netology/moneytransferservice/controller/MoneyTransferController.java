package ru.netology.moneytransferservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.moneytransferservice.model.ConfirmRequestDto;
import ru.netology.moneytransferservice.model.TransferRequestDto;
import ru.netology.moneytransferservice.model.TransferResponseDto;
import ru.netology.moneytransferservice.service.MoneyTransferService;

@AllArgsConstructor
@RestController
@CrossOrigin
public class MoneyTransferController {

    private final MoneyTransferService service;

    @PostMapping("/transfer")
    public TransferResponseDto transfer(@RequestBody TransferRequestDto transferRequestDto) {
        return service.transferMoney(transferRequestDto);
    }

    @PostMapping("/confirmOperation")
    public TransferResponseDto confirmOperation(@RequestBody ConfirmRequestDto confirmRequestDto) {
        return service.confirmOperation(confirmRequestDto);
    }

}
