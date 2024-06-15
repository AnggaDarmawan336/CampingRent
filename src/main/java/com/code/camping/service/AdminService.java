package com.code.camping.service;

import com.code.camping.entity.Admin;
import com.code.camping.utils.dto.request.LoginAdminRequest;
import com.code.camping.utils.dto.request.RegisterAdminRequest;
import com.code.camping.utils.dto.response.LoginAdminResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminService {
	Admin create(RegisterAdminRequest request);
	LoginAdminResponse login(LoginAdminRequest request);
	Admin get_by_id(String id);
	Page<Admin> get_all(Pageable pageable, RegisterAdminRequest registerAdminRequest);
	Admin update(RegisterAdminRequest request);
	void delete(String id);
}
