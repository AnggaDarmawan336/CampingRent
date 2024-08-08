package com.code.camping.utils.dto.response;

import java.util.Date;
import java.util.List;

import com.code.camping.entity.Transaction;
import com.code.camping.entity.Weather;
import com.code.camping.service.WeatherService;
import com.code.camping.utils.dto.webResponse.PageResponse;

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
    private Date dateStart;
    private Date dateEnd;
    private Integer duration;
    private Integer total;


    public static TransactionResponse fromTransaction(Transaction transaction){
        String user_id = (transaction.getUser() != null) ? transaction.getUser().getId() : null;
        String product_id = (transaction.getProduct() != null) ? transaction.getProduct().getId() : null;
       
        return TransactionResponse.builder()
                .id(transaction.getId())
                .quantity(transaction.getQuantity())
                .price_history(transaction.getPrice_history())
                .user_id(user_id)
                .product_id(product_id)
                .dateStart(transaction.getDateStart())
                .dateEnd(transaction.getDateEnd())
                .duration(transaction.getDuration())
                .total(transaction.getTotal())
                .build();
    }

  

}
