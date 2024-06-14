package com.code.camping.repository;

import com.code.camping.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AdminRepository extends JpaRepository<Admin,String>, JpaSpecificationExecutor<Admin> {
	Admin findByEmail(String email);
}
