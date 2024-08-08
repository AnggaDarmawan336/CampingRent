package com.code.camping.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;
import com.code.camping.entity.Transaction;
import com.code.camping.entity.Wallet;
import com.code.camping.security.JwtUtils;
import com.code.camping.service.AdminService;
import com.code.camping.service.TransactionService;
import com.code.camping.service.UserService;
import com.code.camping.service.WalletService;
import com.code.camping.utils.dto.request.TransactionRequest;
import com.code.camping.utils.dto.response.TransactionResponse;
import com.code.camping.utils.dto.webResponse.PageResponse;
import com.code.camping.utils.dto.webResponse.Res;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.Date;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;



@AllArgsConstructor
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transaction_service;
    private final UserService user_service;
    private final JwtUtils jwtUtils;
    private final AdminService admin_service;
   

    @PostMapping
    public ResponseEntity<?> create(@RequestHeader(name = "Authorization")String access_token, @RequestBody TransactionRequest request) {
        Claims jwtPayload = jwtUtils.decodeAccessToken(access_token);
        Date currentDate = new Date();
        String userIdFromToken = jwtPayload.getSubject();
        String user_id = user_service.getById(userIdFromToken).getId();
        boolean isUserIdJWTequalsUserIdReqParams = userIdFromToken.equals(user_id);
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());
       

        if (isUserIdJWTequalsUserIdReqParams && isTokenNotYetExpired) {
                TransactionResponse response = TransactionResponse.fromTransaction(transaction_service.create(request, userIdFromToken));
                return  Res.renderJson(response, "Transaction Created Successfully", HttpStatus.CREATED);
        } 
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied or Token expired");
        }
        
    }
    
    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestHeader(name = "Authorization") String access_token,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page,
            @ModelAttribute TransactionRequest request) {

        Claims jwtPayload = jwtUtils.decodeAccessToken(access_token);
        Date currentDate = new Date();  
        String userIdFromToken = jwtPayload.getSubject();
        String admin_id = admin_service.getById(userIdFromToken).getId();
        boolean isUserIdJWTequalsUserIdReqParams = userIdFromToken.equals(admin_id);
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isUserIdJWTequalsUserIdReqParams && isTokenNotYetExpired) {
            PageResponse<Transaction> res = new PageResponse<>(transaction_service.getAll(page, request));
            return Res.renderJson(res, "ok", HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied or Token expired");
        }

    }


    @GetMapping(path = "/id")
    public ResponseEntity<?> getById(@RequestHeader(name = "Authorization") String access_token) {
        Claims jwtPayload = jwtUtils.decodeAccessToken(access_token);
        Date currentDate = new Date();String userIdFromToken = jwtPayload.getSubject();
        String user_id = user_service.getById(userIdFromToken).getId();
        boolean isUserIdJWTequalsUserIdReqParams = userIdFromToken.equals(user_id);
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isUserIdJWTequalsUserIdReqParams && isTokenNotYetExpired) {
            return Res.renderJson((transaction_service.findByUserId(user_id)), "product ID Retrieved Successfully", HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied or Token expired");}
    }


    

    @PutMapping(path = "/update")
    public ResponseEntity<?> update(@RequestHeader(name = "Authorization") String access_token, @RequestBody TransactionRequest request) {
        Claims jwtPayload = jwtUtils.decodeAccessToken(access_token);
        Date currentDate = new Date();
        String ProductIdFromToken = jwtPayload.getSubject();
        String user_id = user_service.getById(ProductIdFromToken).getId();
        boolean isProductIdJWTequalsProductIdReqParams = ProductIdFromToken.equals(user_id);
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isProductIdJWTequalsProductIdReqParams && isTokenNotYetExpired) {
            Transaction updatedProduct = transaction_service.update(request , ProductIdFromToken);
            return ResponseEntity.ok(TransactionResponse.fromTransaction(updatedProduct));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied or Token expired");
        }
    }


    @DeleteMapping(path = "/{id_product}")
    public ResponseEntity<?> delete(@RequestHeader(name = "Authorization") String access_token, @PathVariable String id_product) {
        Claims jwtPayload = jwtUtils.decodeAccessToken(access_token);
        Date currentDate = new Date();
        boolean isProductIdJWTequalsProductIdReqParams = jwtPayload.getSubject().equals(admin_service.getById(jwtPayload.getSubject()).getId());
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isProductIdJWTequalsProductIdReqParams && isTokenNotYetExpired) {
            try {
                transaction_service.delete(id_product);
                return Res.renderJson(null, "Product Deleted Successfully", HttpStatus.OK);
            } catch (Exception e) {
                return Res.renderJson(null, "Failed to Delete Product", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied or Token expired");
        }

    }



    


    
}
