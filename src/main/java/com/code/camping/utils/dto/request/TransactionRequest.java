package com.code.camping.utils.dto.request;

import com.code.camping.entity.Product;
import com.code.camping.entity.Transaction;
import com.code.camping.entity.User;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {

    private String id;
    @NotNull(message = "Quantity cannot be null")
    private Integer quantity;
    private Integer price_history;
    private String user_id;
    private String product_id;

    public Transaction convert(){
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setQuantity(quantity);
        transaction.setPrice_history(price_history);

        if(user_id != null){
            User transactionUser = new User();
            transactionUser.setId(user_id);
            transaction.setUser(transactionUser);
        }

        if(product_id != null){
            Product transactionProduct = new Product();
            transactionProduct.setId(product_id);
            transaction.setProduct(transactionProduct);
        }

        return transaction;
    }
}
