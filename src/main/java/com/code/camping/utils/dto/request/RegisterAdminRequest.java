package com.code.camping.utils.dto.request;

import com.code.camping.entity.Admin;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAdminRequest {

	private String id;

	@NotBlank(message = "Name cannot be blank")
	private String name;

	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$",
			message = "Please provide a valid email")
	@NotBlank
	private String email;

	@NotBlank(message = "Password cannot be blank")
	@NotNull(message = "Password cannot be empty")
	private String password;

	public static Admin fromRegisterToAdminMapper(RegisterAdminRequest registerAdminRequest){
		return Admin.builder()
				.id(registerAdminRequest.id)
				.name(registerAdminRequest.name)
				.email(registerAdminRequest.email)
				.password(registerAdminRequest.password)
				.build();
	}
}
