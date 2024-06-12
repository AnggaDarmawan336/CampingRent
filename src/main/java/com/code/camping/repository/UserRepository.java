package com.code.camping.repository;

import com.code.camping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    User findByEmail(String email);
}
