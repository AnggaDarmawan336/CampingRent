package com.code.camping.service;

import com.code.camping.entity.User;
import com.code.camping.utils.dto.request.LoginUserRequest;
import com.code.camping.utils.dto.request.RegisterUserRequest;
import com.code.camping.utils.dto.response.LoginUserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User create(RegisterUserRequest request);
    LoginUserResponse login(LoginUserRequest request);
}
