package com.code.camping.service.impl;

import com.code.camping.entity.Transaction;
import com.code.camping.service.TransactionService;
import com.code.camping.utils.dto.request.TransactionRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    @Override
    public Transaction create(TransactionRequest request) {
        return null;
    }

    @Override
    public Page<Transaction> getAll(Pageable pageable, TransactionRequest request) {
        return null;
    }

    @Override
    public Transaction getById(String id) {
        return null;
    }

    @Override
    public Transaction update(TransactionRequest request) {
        return null;
    }

    @Override
    public void delete(String id) {

    }
}
