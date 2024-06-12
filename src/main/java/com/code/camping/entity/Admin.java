package com.code.camping.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admin")

public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String email;
    private String password;

}
