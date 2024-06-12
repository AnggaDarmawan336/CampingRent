package com.code.camping.utils.dto.response;

import com.code.camping.entity.Product;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class ProductResponse {

    private String id;
    private String name;
    private Integer price;

    public static ProductResponse fromProduct(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
