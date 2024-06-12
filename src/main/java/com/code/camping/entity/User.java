package com.code.camping.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String email;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private List<Wallet> wallet_list;
    
}
