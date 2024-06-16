package com.code.camping.utils.dto.response;

import com.code.camping.entity.Transaction;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class TransactionResponse {

    private String id;
    private Integer quantity;
    private Integer price_history;
    private String user_id;
    private String product_id;

    public static TransactionResponse fromTransaction(Transaction transaction) {
        String user_id = (transaction.getUser() != null) ? transaction.getUser().getId() : null;
        String product_id = (transaction.getProduct() != null) ? transaction.getProduct().getId() : null;

        return TransactionResponse.builder()
                .id(transaction.getId())
                .quantity(transaction.getDay())
                .price_history(transaction.getPrice_history())
                .user_id(user_id)
                .product_id(product_id)
                .build();
    }


}
