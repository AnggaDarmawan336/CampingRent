package com.code.camping.controller;

import com.code.camping.entity.User;
import com.code.camping.security.JwtUtils;
import com.code.camping.service.AdminService;
import com.code.camping.service.UserService;
import com.code.camping.utils.dto.request.LoginUserRequest;
import com.code.camping.utils.dto.request.RegisterUserRequest;
import com.code.camping.utils.dto.response.LoginUserResponse;
import com.code.camping.utils.dto.response.UserResponse;
import com.code.camping.utils.dto.webResponse.PageResponse;
import com.code.camping.utils.dto.webResponse.Res;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final AdminService adminService;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> create(@Valid @RequestBody RegisterUserRequest request) {
        UserResponse response = UserResponse.fromUser(userService.create(request));
        return Res.renderJson(response, "Register User Created Successfully", HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public LoginUserResponse login(@RequestBody LoginUserRequest request) {
        return userService.login(request);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getOne(@RequestHeader(name = "Authorization") String accessToken, @PathVariable String id) {
        Claims jwtPayload = jwtUtils.decodeAccessToken(accessToken);
        Date currentDate = new Date();
        boolean isUserIdJWTequalsUserIdReqParams = jwtPayload.getSubject().equals(id);
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());
        if (isUserIdJWTequalsUserIdReqParams && isTokenNotYetExpired) {
            return Res.renderJson(UserResponse.fromUser(userService.getById(id)), "User ID Retrieved Successfully", HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to Find");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestHeader(name = "Authorization") String accessToken,
            @PageableDefault(page = 0,size = 10,sort = "id",direction = Sort.Direction.ASC) Pageable page,
            @ModelAttribute RegisterUserRequest registerUserRequest
    ){
        Claims jwtPayload = jwtUtils.decodeAccessToken(accessToken);
        Date currentDate = new Date();
        String getToken = jwtPayload.getSubject();
        String getAdmin = adminService.getById(getToken).getId();
        boolean isAdminIdJWTEqualsAdminIdReqParams = jwtPayload.getSubject().equals(getAdmin);
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isAdminIdJWTEqualsAdminIdReqParams && isTokenNotYetExpired){
            PageResponse<User> res = new PageResponse<>(userService.getAll(page,registerUserRequest));
            return Res.renderJson(res,"ok",HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestHeader(name = "Authorization") String accessToken, @RequestBody RegisterUserRequest request) {
        Claims jwtPayload = jwtUtils.decodeAccessToken(accessToken);
        Date currentDate = new Date();
        String userIdFromToken = jwtPayload.getSubject();
        boolean isUserIdJWTequalsUserIdReqParams = userIdFromToken.equals(request.getId());
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isUserIdJWTequalsUserIdReqParams && isTokenNotYetExpired) {
            User updatedUser = userService.update(request);
            return ResponseEntity.ok(UserResponse.fromUser(updatedUser));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to Find");
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@RequestHeader(name = "Authorization") String accessToken, @PathVariable String id) {
        Claims jwtPayload = jwtUtils.decodeAccessToken(accessToken);
        Date currentDate = new Date();
        boolean isUserIdJWTequalsUserIdReqParams = jwtPayload.getSubject().equals(id);
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isUserIdJWTequalsUserIdReqParams && isTokenNotYetExpired) {
            try {
                userService.delete(id);
                return Res.renderJson(null, "User Deleted Successfully", HttpStatus.OK);
            } catch (Exception e) {
                return Res.renderJson(null, "Failed to Delete User", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed to Find");
        }
    }
}
