package com.code.camping.service.impl;

import com.code.camping.entity.Admin;
import com.code.camping.repository.AdminRepository;
import com.code.camping.security.JwtUtils;
import com.code.camping.service.AdminService;
import com.code.camping.utils.dto.request.LoginAdminRequest;
import com.code.camping.utils.dto.request.RegisterAdminRequest;
import com.code.camping.utils.dto.response.LoginAdminResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
	private final AdminRepository admin_repository;
	private JwtUtils jwtUtils;

	@Override
	public Admin create(RegisterAdminRequest request) {
		Admin admin = RegisterAdminRequest.fromRegisterToAdminMapper(request);
		String hashedPassword = new BCryptPasswordEncoder().encode(request.getPassword());
		admin.setPassword(hashedPassword);
		return admin_repository.saveAndFlush(admin);
	}

	@Override
	public LoginAdminResponse login(LoginAdminRequest request) {
		LoginAdminResponse loginAdminResponse = new LoginAdminResponse();
		loginAdminResponse.setAccess_token("");
		try {
			Admin admin = admin_repository.findByEmail(request.getEmail());
			if (admin.getEmail() != null){
				Boolean isPasswordMatchAdmin = new BCryptPasswordEncoder().matches(request.getPassword(), admin.getPassword());
				if (new BCryptPasswordEncoder().matches(request.getPassword(), admin.getPassword())){
					String accessTokenForAdmin = jwtUtils.generateAccessTokenForAdmin(admin);
					loginAdminResponse.setAccess_token(accessTokenForAdmin);
				}
			}
			return loginAdminResponse;
		} catch (Exception error){
			return loginAdminResponse;
		}
	}

	@Override
	public Admin get_by_id(String id) {
		return admin_repository.findById(id).orElseThrow(() -> new HttpServerErrorException(
				HttpStatus.NOT_FOUND,"Admin with id " + id + " is not found"));
	}

	@Override
	public Admin update(RegisterAdminRequest request) {
		Admin existing_admin = admin_repository.findById(request.getId())
				.orElseThrow(() -> new HttpServerErrorException(
						HttpStatus.NOT_FOUND,"Admin with id " + request.getId() + " is not found"));
		existing_admin.setName(request.getName());
		existing_admin.setEmail(request.getEmail());
		if (request.getPassword() != null && !request.getPassword().isEmpty()){
			String hashedPasswordAdmin = new BCryptPasswordEncoder().encode(request.getPassword());
			existing_admin.setPassword(hashedPasswordAdmin);
		}
		return admin_repository.saveAndFlush(existing_admin);
	}

	@Override
	public void delete(String id) {
		this.get_by_id(id);
		admin_repository.deleteById(id);
	}
}
