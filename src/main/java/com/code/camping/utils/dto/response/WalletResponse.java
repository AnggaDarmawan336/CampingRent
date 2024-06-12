package com.code.camping.utils.dto.response;

import com.code.camping.entity.Wallet;
import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
public class WalletResponse {

    private String id;
    private String walletType;
    private Integer balance;
    private String user_id;

    public static WalletResponse fromWallet(Wallet wallet){
        String user_id = (wallet.getUser() != null) ? wallet.getUser().getId() : null;

        return WalletResponse.builder()
                .id(wallet.getId())
                .walletType(wallet.getWalletType())
                .balance(wallet.getBalance())
                .user_id(user_id)
                .build();
    }
}
