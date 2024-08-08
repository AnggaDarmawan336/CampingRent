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
            User user = userRepository.findByEmail(request.getEmail());
            if(user.getEmail() != null) {
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

    @Override
    public User getById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND, "User with id " + id + " is not found"));
    }

    @Override
    public Page<User> getAll(Pageable pageable, RegisterUserRequest registerUserRequest) {
        Specification<User> specification = GeneralSpecification.getSpecification(registerUserRequest);
        return userRepository.findAll(specification,pageable);
    }

    @Override
    public User update(RegisterUserRequest request) {
        User existingUser = userRepository.findById(request.getId())
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND, "User with id " + request.getId() + " is not found"));
        existingUser.setName(request.getName());
        existingUser.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            String hashedPassword = new BCryptPasswordEncoder().encode(request.getPassword());
            existingUser.setPassword(hashedPassword);
        }

        return userRepository.saveAndFlush(existingUser);
    }

    @Override
    public void delete(String id) {
        this.getById(id);
        userRepository.deleteById(id);
    }
}
