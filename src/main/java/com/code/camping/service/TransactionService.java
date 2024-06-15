package com.code.camping.service;

import com.code.camping.entity.Transaction;
import com.code.camping.utils.dto.request.TransactionRequest;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {
    Transaction create(TransactionRequest request, String id);
    Page<Transaction> getAll(Pageable pageable, TransactionRequest request);
    Transaction getById(String id);
    Transaction update(TransactionRequest request);
    void delete(String id);
    List<Transaction> findByUserId(String userId);

}
