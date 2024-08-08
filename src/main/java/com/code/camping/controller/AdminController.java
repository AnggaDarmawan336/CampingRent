package com.code.camping.controller;

import com.code.camping.entity.Admin;
import com.code.camping.security.JwtUtils;
import com.code.camping.service.AdminService;
import com.code.camping.utils.dto.request.LoginAdminRequest;
import com.code.camping.utils.dto.request.RegisterAdminRequest;
import com.code.camping.utils.dto.response.AdminResponse;
import com.code.camping.utils.dto.response.LoginAdminResponse;
import com.code.camping.utils.dto.webResponse.PageResponse;
import com.code.camping.utils.dto.webResponse.Res;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/admins")
@AllArgsConstructor
public class AdminController {

	private final AdminService adminService;
	private final JwtUtils jwtUtils;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterAdminRequest request) {
		AdminResponse adminResponse = AdminResponse.fromAdmin(adminService.create(request));
		return Res.renderJson(adminResponse,"Register Admin created successfully",HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public LoginAdminResponse login(@RequestBody LoginAdminRequest request){
		return adminService.login(request);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getById(
			@PathVariable String id,
			@RequestHeader(name = "Authorization") String accessToken
	){
		Claims jwtPayload = jwtUtils.decodeAccessToken(accessToken);
		Date currentDate = new Date();
		boolean isAdminIdJWTEqualsAdminIdReqParams = jwtPayload.getSubject().equals(id);
		boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());
		if (isAdminIdJWTEqualsAdminIdReqParams && isTokenNotYetExpired) {
			return Res.renderJson(AdminResponse.fromAdmin(adminService.getById(id)),
					"Admin ID Retrieved Successfully",HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to Find");
		}
	}

	@GetMapping
	public ResponseEntity<?> getAll(
			@RequestHeader(name = "Authorization") String accessToken,
			@PageableDefault(page = 0,size = 10,sort = "id",direction = Sort.Direction.ASC) Pageable page,
			@ModelAttribute RegisterAdminRequest registerAdminRequest
	){
		Claims jwtPayload = jwtUtils.decodeAccessToken(accessToken);
		Date currentDate = new Date();
		String getToken = jwtPayload.getSubject();
		String getAdmin = adminService.getById(getToken).getId();
		boolean isAdminIdJWTEqualsAdminIdReqParams = jwtPayload.getSubject().equals(getAdmin);
		boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());
		if (isAdminIdJWTEqualsAdminIdReqParams && isTokenNotYetExpired){
			PageResponse<Admin> res = new PageResponse<>(adminService.getAll(page, registerAdminRequest));
			return Res.renderJson(res, "ok", HttpStatus.OK);
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to Find");
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(
			@RequestHeader(name = "Authorization") String accessToken,
			@RequestBody RegisterAdminRequest request
	){
		Claims jwtPayload = jwtUtils.decodeAccessToken(accessToken);
		Date currentDate = new Date();
		boolean isAdminIdJWTEqualsAdminIdReqParams = jwtPayload.getSubject().equals(request.getId());
		boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());
		if (isAdminIdJWTEqualsAdminIdReqParams && isTokenNotYetExpired) {
			Admin admin = adminService.update(request);
			return ResponseEntity.ok(AdminResponse.fromAdmin(admin));
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to Find");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(
			@PathVariable String id,
			@RequestHeader(name = "Authorization") String accessToken
	){
		Claims jwtPayLoad = jwtUtils.decodeAccessToken(accessToken);
		Date currentDate = new Date();
		boolean isAdminIdJWTEqualsAdminIdReqParams = jwtPayLoad.getSubject().equals(id);
		boolean isTokenNotYetExpired = currentDate.before(jwtPayLoad.getExpiration());
		if (isAdminIdJWTEqualsAdminIdReqParams && isTokenNotYetExpired){
			try {
				adminService.delete(id);
				return Res.renderJson(null,"Admin deleted successfully",HttpStatus.OK);
			} catch (Exception error){
				return Res.renderJson(null,"Failed to delete admin",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to Find");
		}
	}
}
