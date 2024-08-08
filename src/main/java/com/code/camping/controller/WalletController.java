package com.code.camping.controller;

import com.code.camping.entity.Wallet;
import com.code.camping.security.JwtUtils;
import com.code.camping.service.AdminService;
import com.code.camping.service.UserService;
import com.code.camping.service.WalletService;
import com.code.camping.utils.dto.request.WalletRequest;
import com.code.camping.utils.dto.response.WalletResponse;
import com.code.camping.utils.dto.webResponse.PageResponse;
import com.code.camping.utils.dto.webResponse.Res;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;

import java.util.Date;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService walletService;
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody WalletRequest request, @RequestHeader(name = "Authorization") String accessToken) {
        
        Claims jwtPayload = jwtUtils.decodeAccessToken(accessToken);
        Date currentDate = new Date();
        boolean isProductIdJWTequalsProductIdReqParams = jwtPayload.getSubject().equals(adminService.getById(jwtPayload.getSubject()).getId());
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isProductIdJWTequalsProductIdReqParams && isTokenNotYetExpired) {
            Wallet wallet = walletService.create(request);
            WalletResponse response = WalletResponse.fromWallet(wallet);
            return Res.renderJson(response, "Product ID Retrieved Successfully", HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied or Token expired");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestHeader(name = "Authorization") String accessToken,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page,
            @ModelAttribute WalletRequest request) {
        
        Claims jwtPayload = jwtUtils.decodeAccessToken(accessToken);
        Date currentDate = new Date();
        String getToken = jwtPayload.getSubject();
        String getAdmin = adminService.getById(getToken).getId();
        boolean isProductIdJWTequalsProductIdReqParams = jwtPayload.getSubject().equals(getAdmin);
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isProductIdJWTequalsProductIdReqParams && isTokenNotYetExpired) {
            PageResponse<Wallet> res = new PageResponse<>(walletService.getAll(page, request));
            return Res.renderJson(res, "ok", HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied or Token expired");
        }

    }

    @GetMapping(path = "/id")
    public ResponseEntity<?> getById(@RequestHeader(name = "Authorization") String accessToken) {
        Claims jwtPayload = jwtUtils.decodeAccessToken(accessToken);
        Date currentDate = new Date();
        boolean isProductIdJWTequalsProductIdReqParams = jwtPayload.getSubject().equals(userService.getById(jwtPayload.getSubject()).getId());
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isProductIdJWTequalsProductIdReqParams && isTokenNotYetExpired) {
            return Res.renderJson(WalletResponse.fromWallet(walletService.fineByUserId(userService.getById(jwtPayload.getSubject()).getId())), "product ID Retrieved Successfully", HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied or Token expired");}
    }


    @PutMapping(path = "/update")
    public ResponseEntity<?> update(@RequestHeader(name = "Authorization") String access_token, @RequestBody WalletRequest request) {
        Claims jwtPayload = jwtUtils.decodeAccessToken(access_token);
        Date currentDate = new Date();
        boolean isProductIdJWTequalsProductIdReqParams = jwtPayload.getSubject().equals(adminService.getById(jwtPayload.getSubject()).getId());
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isProductIdJWTequalsProductIdReqParams && isTokenNotYetExpired) {
            Wallet updatedProduct = walletService.update(request);
            return ResponseEntity.ok(WalletResponse.fromWallet(updatedProduct));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied or Token expired");
        }
    }

    
    @DeleteMapping(path = "/{walletId}")
    public ResponseEntity<?> delete(@RequestHeader(name = "Authorization") String accessToken, @PathVariable String walletId) {
        Claims jwtPayload = jwtUtils.decodeAccessToken(accessToken);
        Date currentDate = new Date();
        boolean isProductIdJWTequalsProductIdReqParams = jwtPayload.getSubject().equals(adminService.getById(jwtPayload.getSubject()).getId());
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isProductIdJWTequalsProductIdReqParams && isTokenNotYetExpired) {
            try {
                walletService.delete(walletId);
                return Res.renderJson(null, "Wallet Deleted Successfully", HttpStatus.OK);
            } catch (Exception e) {
                return Res.renderJson(null, "Failed to Delete Wallet", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied or Token expired");
        }

    }

}
