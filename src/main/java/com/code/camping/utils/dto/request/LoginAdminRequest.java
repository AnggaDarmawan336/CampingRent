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

    public static Admin fromLoginRequestToAdminMapper(LoginAdminRequest loginAdminRequest){
        return Admin.builder()
            .email(loginAdminRequest.email)
            .password(loginAdminRequest.password)
            .build();


    }

}
