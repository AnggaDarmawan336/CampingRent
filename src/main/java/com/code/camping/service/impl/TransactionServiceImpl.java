package com.code.camping.service.impl;

import com.code.camping.entity.Product;
import com.code.camping.entity.Transaction;
import com.code.camping.entity.Wallet;
import com.code.camping.repository.ProductRepository;
import com.code.camping.repository.TransactionRepository;
import com.code.camping.repository.WalletRepository;
import com.code.camping.service.ProductService;
import com.code.camping.service.TransactionService;
import com.code.camping.service.WalletService;
import com.code.camping.utils.dto.request.TransactionRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final ProductService productService;
    private final WalletService walletService;

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
        Transaction existingTransaction = transactionRepository.getById(request.getId());
        Wallet wallet = existingTransaction.getWallet();

        Integer oldQuantity = existingTransaction.getQuantity();
        Integer newQuantity = request.getQuantity();
        Integer price = existingTransaction.getPrice_history();

        Integer oldTotal = oldQuantity * price;
        Integer newTotal = newQuantity * price;
        Integer difference = newTotal - oldTotal;

        wallet.setBalance(wallet.getBalance() - difference);
        walletRepository.save(wallet);

        existingTransaction.setQuantity(newQuantity);

        return transactionRepository.save(existingTransaction);

    }

    @Override
    public void delete(String id) {

    }
}
