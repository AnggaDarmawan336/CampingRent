package com.code.camping.utils.dto.response;

import com.code.camping.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponse {
	private String id;
	private String email;
	private String password;

	public static AdminResponse fromAdmin(Admin admin) {
		return AdminResponse.builder()
				.id(admin.getId())
				.email(admin.getEmail())
				.password(admin.getPassword())
				.build();
	}
}
