package com.code.camping.repository;

import com.code.camping.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String>, JpaSpecificationExecutor<Wallet> {
}
