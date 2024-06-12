package com.code.camping.utils.dto.response;

import com.code.camping.entity.User;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private String id;
    private String email;
    private String password;

    public static UserResponse fromUser(User user){
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

}
