package com.code.camping.service;

import com.code.camping.entity.Admin;
import com.code.camping.utils.dto.request.LoginAdminRequest;
import com.code.camping.utils.dto.request.RegisterAdminRequest;
import com.code.camping.utils.dto.response.LoginAdminResponse;

import java.util.List;

public interface AdminService {
	Admin create(RegisterAdminRequest request);
	LoginAdminResponse login(LoginAdminRequest request);
	Admin get_by_id(String id);
	Admin update(RegisterAdminRequest request);
	void delete(String id);
}
