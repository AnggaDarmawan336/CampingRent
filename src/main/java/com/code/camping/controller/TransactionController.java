package com.code.camping.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.code.camping.entity.Transaction;
import com.code.camping.entity.Wallet;
import com.code.camping.security.JwtUtils;
import com.code.camping.service.TransactionService;
import com.code.camping.service.WalletService;
import com.code.camping.utils.dto.request.TransactionRequest;
import com.code.camping.utils.dto.response.TransactionResponse;
import com.code.camping.utils.dto.webResponse.Res;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@AllArgsConstructor
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transaction_service;
    private final JwtUtils jwtUtils;
    private final WalletService wallet_service;

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader(name = "Authorization")String access_token, @RequestBody TransactionRequest request, @RequestParam String wallet_id) {
        Claims jwtPayload = jwtUtils.decodeAccessToken(access_token);
        Date currentDate = new Date();
        String userIdFromToken = jwtPayload.getSubject();
        String user_id = request.getUser_id();
        boolean isUserIdJWTequalsUserIdReqParams = userIdFromToken.equals(user_id);
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());
       

        if (isUserIdJWTequalsUserIdReqParams && isTokenNotYetExpired) {
                TransactionResponse response = TransactionResponse.fromTransaction(transaction_service.create(request, wallet_id));
                return  Res.renderJson(response, "Transaction Created Successfully", HttpStatus.CREATED);
        } 
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied or Token expired");
        }
        
    }
    
}
