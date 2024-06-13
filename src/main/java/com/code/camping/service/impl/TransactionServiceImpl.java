package com.code.camping.service.impl;

import com.code.camping.entity.Product;
import com.code.camping.entity.Transaction;
import com.code.camping.entity.User;
import com.code.camping.entity.Wallet;
import com.code.camping.repository.TransactionRepository;
import com.code.camping.repository.WalletRepository;
import com.code.camping.service.ProductService;
import com.code.camping.service.TransactionService;
import com.code.camping.service.UserService;
import com.code.camping.service.WalletService;
import com.code.camping.utils.dto.request.TransactionRequest;
import com.code.camping.utils.dto.request.WalletRequest;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transaction_repository;
    private final WalletService wallet_service;
    private final ProductService product_service;

    @Override
    public Transaction create(TransactionRequest request,String wallet_id) {

        Wallet wallet = wallet_service.getById(wallet_id);
        String product_id = request.getProduct_id();
        Product product = product_service.getById(product_id);
        WalletRequest wallet_request = new WalletRequest();
       
        Integer product_price = product.getPrice();
        Integer balance = wallet.getBalance();
        Integer total_price = request.getQuantity() * product_price;

        if (balance >= total_price && wallet.getUser().getId().equals(request.getUser_id())) {

            wallet_request.setId(wallet_id);
            wallet_request.setBalance(wallet.getBalance());
            wallet_request.setUser_id(wallet.getUser().getId());
            wallet_request.setWalletType(wallet.getWalletType());
            wallet_request.setBalance(balance - total_price);

            wallet_service.update(wallet_request);

            return transaction_repository.saveAndFlush(request.convert());
        } 

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
