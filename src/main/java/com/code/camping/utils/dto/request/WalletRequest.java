package com.code.camping.utils.dto.request;

import com.code.camping.entity.User;
import com.code.camping.entity.Wallet;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WalletRequest {

    private String id;

    @NotBlank(message = "Wallet type cannot be blank")
    private String walletType;

    @NotNull(message = "Balance cannot be null")
    private Integer balance;

    @NotBlank(message = "User ID cannot be blank")
    private String user_id;

    public Wallet convert(){
        Wallet wallet = new Wallet();
        wallet.setId(id);
        wallet.setWalletType(walletType);
        wallet.setBalance(balance);
        if(user_id != null){
            User userWallet = new User();
            userWallet.setId(user_id);
            wallet.setUser(userWallet);
        }
        return wallet;
    }
}
