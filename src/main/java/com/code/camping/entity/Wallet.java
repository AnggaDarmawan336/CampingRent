package com.code.camping.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "wallets")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String walletType;
    private Integer balance;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    @JsonIgnore
    private User user;
}
