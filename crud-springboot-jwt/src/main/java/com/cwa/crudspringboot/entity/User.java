package com.cwa.crudspringboot.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String role;
    private String resetPasswordToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date resetPasswordTokenExpiry;
}
