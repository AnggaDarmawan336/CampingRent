package com.code.camping.utils.dto.request;

import com.code.camping.entity.Admin;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Data
public class LoginAdminRequest {
    private String email;
    private String password;

    public static Admin fromLoginRequestToAdminMapper(LoginAdminRequest loginAdminRequest){
        return Admin.builder()
            .email(loginAdminRequest.email)
            .password(loginAdminRequest.password)
            .build();


    }

}
