package com.code.camping.controller;

import com.code.camping.entity.Product;
import com.code.camping.service.ProductService;
import com.code.camping.utils.dto.request.ProductRequest;
import com.code.camping.utils.dto.response.ProductResponse;
import com.code.camping.utils.dto.webResponse.PageResponse;
import com.code.camping.utils.dto.webResponse.Res;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService product_service;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ProductRequest request) {
        ProductResponse response = ProductResponse.fromProduct(product_service.create(request));
        return Res.renderJson(response, "Product Created Successfully", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> index(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page,
            @ModelAttribute ProductRequest request) {
        PageResponse<Product> res = new PageResponse<>(product_service.getAll(page, request));
        return Res.renderJson(res, "ok", HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getOne(@PathVariable String id) {
        return Res.renderJson(ProductResponse.fromProduct(product_service.getById(id)), "Product ID Retrieved Successfully", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Product request) {
        ProductResponse response = ProductResponse.fromProduct(product_service.update(request));
        return Res.renderJson(response, "Product Update Successfully", HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            product_service.delete(id);
            return Res.renderJson(null, "Product Deleted Successfully", HttpStatus.OK);
        } catch (Exception e) {
            return Res.renderJson(null, "Failed to Delete Product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
