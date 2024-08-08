package com.code.camping.controller;

import com.code.camping.entity.Product;
import com.code.camping.entity.User;
import com.code.camping.security.JwtUtils;
import com.code.camping.service.AdminService;
import com.code.camping.service.ProductService;
import com.code.camping.service.UserService;
import com.code.camping.utils.dto.request.ProductRequest;
import com.code.camping.utils.dto.response.ProductResponse;
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
@RequestMapping("/products")
public class ProductController {

    private final AdminService admin_service;
    private final ProductService product_service;
    private final JwtUtils jwtUtils;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody ProductRequest request, @RequestHeader(name = "Authorization") String access_token) {
        Claims jwtPayload = jwtUtils.decodeAccessToken(access_token);
        Date currentDate = new Date();
        boolean isProductIdJWTequalsProductIdReqParams = jwtPayload.getSubject().equals(admin_service.get_by_id(jwtPayload.getSubject()).getId());
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isProductIdJWTequalsProductIdReqParams && isTokenNotYetExpired) {
            Product product = product_service.create(request);
            ProductResponse response = ProductResponse.fromProduct(product);
            return Res.renderJson(response, "Product ID Retrieved Successfully", HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied or Token expired");
        }
    }


    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestHeader(name = "Authorization") String access_token,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page,
            @ModelAttribute ProductRequest request) {

        Claims jwtPayload = jwtUtils.decodeAccessToken(access_token);
        Date currentDate = new Date();
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isTokenNotYetExpired) {
            PageResponse<Product> res = new PageResponse<>(product_service.getAll(page, request));
            return Res.renderJson(res, "ok", HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token expired");
        }

    }

    // @GetMapping(path = "/{id_product}")
    // public ResponseEntity<?> getById(@RequestHeader(name = "Authorization") String access_token, @PathVariable String id_product, @RequestParam String id) {
    //     Claims jwtPayload = jwtUtils.decodeAccessToken(access_token);
    //     Date currentDate = new Date();
    //     boolean isProductIdJWTequalsProductIdReqParams = jwtPayload.getSubject().equals(id);
    //     boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

    //     if (isProductIdJWTequalsProductIdReqParams && isTokenNotYetExpired) {
    //         return Res.renderJson(ProductResponse.fromProduct(product_service.getById(id_product)), "product ID Retrieved Successfully", HttpStatus.OK);
    //     } else {
    //         return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied or Token expired");}
    // }

    @PutMapping(path = "/update")
    public ResponseEntity<?> update(@RequestHeader(name = "Authorization") String access_token, @RequestBody Product request) {
        Claims jwtPayload = jwtUtils.decodeAccessToken(access_token);
        Date currentDate = new Date();
        boolean isProductIdJWTequalsProductIdReqParams = jwtPayload.getSubject().equals(admin_service.get_by_id(jwtPayload.getSubject()).getId());
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isProductIdJWTequalsProductIdReqParams && isTokenNotYetExpired) {
            Product updatedProduct = product_service.update(request);
            return ResponseEntity.ok(ProductResponse.fromProduct(updatedProduct));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied or Token expired");
        }
    }


    @DeleteMapping(path = "/{id_product}")
    public ResponseEntity<?> delete(@RequestHeader(name = "Authorization") String access_token, @PathVariable String id_product) {
        Claims jwtPayload = jwtUtils.decodeAccessToken(access_token);
        Date currentDate = new Date();
        boolean isProductIdJWTequalsProductIdReqParams = jwtPayload.getSubject().equals(admin_service.get_by_id(jwtPayload.getSubject()).getId());
        boolean isTokenNotYetExpired = currentDate.before(jwtPayload.getExpiration());

        if (isProductIdJWTequalsProductIdReqParams && isTokenNotYetExpired) {
            try {
                product_service.delete(id_product);
                return Res.renderJson(null, "Product Deleted Successfully", HttpStatus.OK);
            } catch (Exception e) {
                return Res.renderJson(null, "Failed to Delete Product", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied or Token expired");
        }

    }
}
