package com.code.camping.utils.dto.request;

import com.code.camping.entity.Product;
import com.code.camping.entity.Transaction;
import com.code.camping.entity.User;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {

    private String id;
    private Integer quantity;
    private Integer price_history;
    @NotNull(message = "User id cannot be null")
    private String user_id;
    private String product_id;
    private Date dateStart;
    private Date dateEnd;
    private Integer total;

    public Transaction convert(){
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setQuantity(quantity);
        transaction.setPrice_history(price_history);
        transaction.setDateStart(dateStart);
        transaction.setDateEnd(dateEnd);
        transaction.setTotal(total);


        if(dateStart != null && dateEnd != null){
            long diffInMillies = Math.abs(dateEnd.getTime() - dateStart.getTime());
            int diff = (int) (diffInMillies / (1000 * 60 * 60 * 24));
            transaction.setDuration(diff);
        }

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
