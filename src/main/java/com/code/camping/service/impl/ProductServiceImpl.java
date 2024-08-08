package com.code.camping.service.impl;

import com.code.camping.entity.Product;
import com.code.camping.repository.ProductRepository;
import com.code.camping.service.ProductService;
import com.code.camping.utils.GeneralSpecification;
import com.code.camping.utils.dto.request.ProductRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository product_repository;

    @Override
    public Product create(ProductRequest request) {
        return product_repository.saveAndFlush(request.convert());
    }

    @Override
    public Page<Product> getAll(Pageable pageable, ProductRequest request) {
        Specification<Product> specification = GeneralSpecification.getSpecification(request);
        return product_repository.findAll(specification, pageable);
    }

    @Override
    public Product getById(String id) {
        return product_repository.findById(id)
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND, "Product with id " + id + " is not found"));
    }

    @Override
    public Product update(Product request) {
        return product_repository.save(request);
    }

    @Override
    public void delete(String id) {
        this.getById(id);
        product_repository.deleteById(id);
    }
}
