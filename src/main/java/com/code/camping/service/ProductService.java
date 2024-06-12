package com.code.camping.service;

import com.code.camping.entity.Product;
import com.code.camping.utils.dto.request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product create(ProductRequest request);
    Page<Product> getAll(Pageable pageable, ProductRequest request);
    Product getById(String id);
    Product update(Product request);
    void delete(String id);
}
