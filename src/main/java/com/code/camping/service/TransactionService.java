package com.code.camping.service;

import com.code.camping.entity.Transaction;
import com.code.camping.utils.dto.request.TransactionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {
    Transaction create(TransactionRequest request);
    Page<Transaction> getAll(Pageable pageable, TransactionRequest request);
    Transaction getById(String id);
    Transaction update(TransactionRequest request);
    void delete(String id);
}
