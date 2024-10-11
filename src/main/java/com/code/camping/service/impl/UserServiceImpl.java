package com.code.camping.service.impl;

import com.code.camping.entity.User;
import com.code.camping.repository.UserRepository;
import com.code.camping.security.JwtUtils;
import com.code.camping.service.UserService;
import com.code.camping.utils.GeneralSpecification;
import com.code.camping.utils.dto.request.LoginUserRequest;
import com.code.camping.utils.dto.request.RegisterUserRequest;
import com.code.camping.utils.dto.response.LoginUserResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    @Override
    public User create(RegisterUserRequest request) {
        User newUser = RegisterUserRequest.fromRegisterToUserMapper(request);
        String hashedPassword = new BCryptPasswordEncoder().encode(request.getPassword());
        newUser.setPassword(hashedPassword);
        return userRepository.saveAndFlush(newUser);
    }

    @Override
    public LoginUserResponse login(LoginUserRequest request) {
        LoginUserResponse loginResponse = new LoginUserResponse();
        loginResponse.setAccessToken("");
        try {
            User user = userRepository.findByUsername(request.getUsername());
            if(user.getUsername() != null) {
                Boolean isPasswordMatch = new BCryptPasswordEncoder().matches(request.getPassword(), user.getPassword());
                if(new BCryptPasswordEncoder().matches(request.getPassword(), user.getPassword())) {
                    String accessToken = jwtUtils.generateAccessToken(user);
                    loginResponse.setAccessToken(accessToken);
                }
            }
            return loginResponse;
        } catch (Exception error) {
            return loginResponse;
        }
    }
}
