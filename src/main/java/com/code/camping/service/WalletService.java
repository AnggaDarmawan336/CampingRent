package com.code.camping.service;

import com.code.camping.entity.Wallet;
import com.code.camping.utils.dto.request.WalletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WalletService {
    Wallet create(WalletRequest request);
    Page<Wallet> getAll(Pageable pageable, WalletRequest request);
    Wallet getById(String id);
    Wallet update(WalletRequest request);
    void delete(String id);
}
