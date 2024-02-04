package ru.netology.moneytransferservice.repository;

import ru.netology.moneytransferservice.model.Amount;
import ru.netology.moneytransferservice.model.Card;
import ru.netology.moneytransferservice.model.Operation;

public interface OperationRepository {
    int createOperation(Card cardFrom, Card cardTo, Amount amount);

    Operation getOperationById(int id);

    void removeOperationById(int id);

}
