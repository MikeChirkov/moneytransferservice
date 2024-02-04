package ru.netology.moneytransferservice.repository;

import ru.netology.moneytransferservice.model.Card;

public interface CardRepository {
    Card getCardByNumber(String cardNumber);

    void saveCard(Card card);
}
