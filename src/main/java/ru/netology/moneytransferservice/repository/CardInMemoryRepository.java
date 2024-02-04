package ru.netology.moneytransferservice.repository;

import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.model.Amount;
import ru.netology.moneytransferservice.model.Card;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CardInMemoryRepository implements CardRepository {
    Map<String, Card> cardMap = new ConcurrentHashMap<>() {
        {
            this.put("1111111111111111", Card.builder()
                    .cardNumber("1111111111111111")
                    .validTill("01/25")
                    .cvv("111")
                    .amount(Amount.builder()
                            .value(1111)
                            .currency("RUR")
                            .build())
                    .build());
            this.put("2222222222222222", Card.builder()
                    .cardNumber("2222222222222222")
                    .validTill("02/25")
                    .cvv("222")
                    .amount(Amount.builder()
                            .value(2222)
                            .currency("RUR")
                            .build())
                    .build());
        }
    };

    @Override
    public Card getCardByNumber(String cardNumber) {
        return cardMap.get(cardNumber);
    }

    @Override
    public void saveCard(Card card) {
        cardMap.put(card.getCardNumber(), card);
    }
}
