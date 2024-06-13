package com.code.camping.controller;

import com.code.camping.entity.User;
import com.code.camping.security.JwtUtils;
import com.code.camping.service.UserService;
import com.code.camping.utils.dto.request.LoginUserRequest;
import com.code.camping.utils.dto.request.RegisterUserRequest;
import com.code.camping.utils.dto.response.LoginUserResponse;
import com.code.camping.utils.dto.response.UserResponse;
import com.code.camping.utils.dto.webResponse.Res;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService user_service;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> create(@Valid @RequestBody RegisterUserRequest request) {
        UserResponse response = UserResponse.fromUser(user_service.create(request));
        return Res.renderJson(response, "Register User Created Successfully", HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public LoginUserResponse login(@RequestBody LoginUserRequest request) {
        return user_service.login(request);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getOne(@RequestHeader(name = "Authorization") String access_token, @PathVariable String id) {
        Claims jwtPayload = jwtUtils.decodeAccessToken(access_token);
        Date currentDate = new Date();
        boolean isUserIdJWTequalsUserIdReqParams = jwtPayload.getSubject().equals(id);
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isUserIdJWTequalsUserIdReqParams && isTokenNotYetExpired) {
            return Res.renderJson(UserResponse.fromUser(user_service.getById(id)), "User ID Retrieved Successfully", HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to Find");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestHeader(name = "Authorization") String access_token, @RequestBody RegisterUserRequest request) {
        Claims jwtPayload = jwtUtils.decodeAccessToken(access_token);
        Date currentDate = new Date();
        String userIdFromToken = jwtPayload.getSubject();
        boolean isUserIdJWTequalsUserIdReqParams = userIdFromToken.equals(request.getId());
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isUserIdJWTequalsUserIdReqParams && isTokenNotYetExpired) {
            User updatedUser = user_service.update(request);
            return ResponseEntity.ok(UserResponse.fromUser(updatedUser));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to Find");
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@RequestHeader(name = "Authorization") String access_token, @PathVariable String id) {
        Claims jwtPayload = jwtUtils.decodeAccessToken(access_token);
        Date currentDate = new Date();
        boolean isUserIdJWTequalsUserIdReqParams = jwtPayload.getSubject().equals(id);
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isUserIdJWTequalsUserIdReqParams && isTokenNotYetExpired) {
            try {
                user_service.delete(id);
                return Res.renderJson(null, "User Deleted Successfully", HttpStatus.OK);
            } catch (Exception e) {
                return Res.renderJson(null, "Failed to Delete User", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to Find");
        }
    }

}
