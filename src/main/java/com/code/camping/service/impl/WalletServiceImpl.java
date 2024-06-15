package com.code.camping.service.impl;

import com.code.camping.entity.Wallet;
import com.code.camping.repository.WalletRepository;
import com.code.camping.service.UserService;
import com.code.camping.service.WalletService;
import com.code.camping.utils.GeneralSpecification;
import com.code.camping.utils.dto.request.WalletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository wallet_repository;
    private final UserService user_service;

    @Override
    public Wallet create(WalletRequest request) {
        return wallet_repository.saveAndFlush(request.convert());
    }

    @Override
    public Page<Wallet> getAll(Pageable pageable, WalletRequest request) {
        Specification<Wallet> specification = GeneralSpecification.get_specification(request);
        return wallet_repository.findAll(specification, pageable);
    }

    @Override
    public Wallet getById(String id) {
        return wallet_repository.findById(id)
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND, "Wallet with id " + id + " is not found"));
    }

    public Wallet fineByUserId(String userId) {
        return wallet_repository.findByUserId(userId);
                
    }

    @Override
    public Wallet update(WalletRequest request) {
        return wallet_repository.saveAndFlush(request.convert());
    }

    @Override
    public void delete(String id) {
        this.getById(id);
        wallet_repository.deleteById(id);
    }
}
