package com.code.camping.utils.dto.request;

import com.code.camping.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Data
public class LoginUserRequest {

    private String username;
    private String password;

    public static User fromLoginRequestToUserMapper(LoginUserRequest loginUserRequest){
        return User.builder()
                .email(loginUserRequest.getUsername())
                .password(loginUserRequest.password)
                .build();
    }
}
