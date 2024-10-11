package com.code.camping.utils.dto.request;

import com.code.camping.entity.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {

    private String id;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$", message = "Please provide a valid email")
    @NotBlank
    private String email;

    @NotBlank(message = "cannot be blank")
    private String username;

    @NotBlank(message = "cannot be blank")
    @NotNull(message = "cannot be empty")
    private String password;

    public static  User fromRegisterToUserMapper(RegisterUserRequest registerUserRequest){
        return User.builder()
                .id(registerUserRequest.id)
                .email(registerUserRequest.email)
                .username(registerUserRequest.username)
                .password(registerUserRequest.password)
                .build();
    }

}
