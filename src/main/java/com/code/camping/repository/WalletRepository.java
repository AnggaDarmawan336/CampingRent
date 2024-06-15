package com.code.camping.repository;

import com.code.camping.entity.User;
import com.code.camping.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface WalletRepository extends JpaRepository<Wallet, String>, JpaSpecificationExecutor<Wallet> {
 

  Wallet findByUserId(String userId);

}