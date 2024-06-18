package com.code.camping.service.impl;

import com.code.camping.entity.Admin;
import com.code.camping.repository.AdminRepository;
import com.code.camping.security.JwtUtils;
import com.code.camping.service.AdminService;
import com.code.camping.utils.GeneralSpecification;
import com.code.camping.utils.dto.request.LoginAdminRequest;
import com.code.camping.utils.dto.request.RegisterAdminRequest;
import com.code.camping.utils.dto.response.LoginAdminResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
	private final AdminRepository adminRepository;
	private JwtUtils jwtUtils;

	@Override
	public Admin create(RegisterAdminRequest request) {
		Admin admin = RegisterAdminRequest.fromRegisterToAdminMapper(request);
		String hashedPassword = new BCryptPasswordEncoder().encode(request.getPassword());
		admin.setPassword(hashedPassword);
		return adminRepository.saveAndFlush(admin);
	}

	@Override
	public LoginAdminResponse login(LoginAdminRequest request) {
		LoginAdminResponse loginAdminResponse = new LoginAdminResponse();
		loginAdminResponse.setAccessToken("");
		try {
			Admin admin = adminRepository.findByEmail(request.getEmail());
			if (admin.getEmail() != null){
				Boolean isPasswordMatchAdmin = new BCryptPasswordEncoder().matches(request.getPassword(), admin.getPassword());
				if (new BCryptPasswordEncoder().matches(request.getPassword(), admin.getPassword())){
					String accessTokenForAdmin = jwtUtils.generateAccessTokenForAdmin(admin);
					loginAdminResponse.setAccessToken(accessTokenForAdmin);
				}
			}
			return loginAdminResponse;
		} catch (Exception error){
			return loginAdminResponse;
		}
	}

	@Override
	public Admin getById(String id) {
		return adminRepository.findById(id).orElseThrow(() -> new HttpServerErrorException(
				HttpStatus.NOT_FOUND,"Admin with id " + id + " is not found"));
	}

	@Override
	public Page<Admin> getAll(Pageable pageable, RegisterAdminRequest registerAdminRequest) {
		Specification<Admin> specification = GeneralSpecification.getSpecification(registerAdminRequest);
		return adminRepository.findAll(specification,pageable);
	}

	@Override
	public Admin update(RegisterAdminRequest request) {
		Admin admin = adminRepository.findById(request.getId())
				.orElseThrow(() -> new HttpServerErrorException(
						HttpStatus.NOT_FOUND,"Admin with id " + request.getId() + " is not found"));
		admin.setName(request.getName());
		admin.setEmail(request.getEmail());
		if (request.getPassword() != null && !request.getPassword().isEmpty()){
			String hashedPasswordAdmin = new BCryptPasswordEncoder().encode(request.getPassword());
			admin.setPassword(hashedPasswordAdmin);
		}
		return adminRepository.saveAndFlush(admin);
	}

	@Override
	public void delete(String id) {
		this.getById(id);
		adminRepository.deleteById(id);
	}
}
