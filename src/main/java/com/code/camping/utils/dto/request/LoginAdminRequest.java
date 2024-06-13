package com.code.camping.utils.dto.request;

import com.code.camping.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LoginAdminRequest {
	private String email;
	private String password;

	public static Admin fromLoginRequestToAdmin(LoginAdminRequest loginAdminRequest){
		return Admin.builder()
				.email(loginAdminRequest.getEmail())
				.password(loginAdminRequest.getPassword())
				.build();
	}
}
