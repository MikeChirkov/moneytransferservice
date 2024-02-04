package ru.netology.moneytransferservice.repository;

import org.springframework.stereotype.Repository;
import ru.netology.moneytransferservice.model.Amount;
import ru.netology.moneytransferservice.model.Card;
import ru.netology.moneytransferservice.model.Operation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class OperationInMemoryRepository implements OperationRepository {
    private final AtomicInteger atomicCounter = new AtomicInteger(0);
    private final Map<Integer, Operation> operationMap = new ConcurrentHashMap<>();

    @Override
    public int createOperation(Card cardFrom, Card cardTo, Amount amount) {
        int id = atomicCounter.incrementAndGet();
        operationMap.put(id, Operation.builder()
                .id(id)
                .cardFrom(cardFrom)
                .cardTo(cardTo)
                .amount(amount)
                .code("0000")
                .build());
        return id;
    }

    @Override
    public Operation getOperationById(int id) {
        return operationMap.get(id);
    }

    @Override
    public void removeOperationById(int id) {
        operationMap.remove(id);
    }
}
