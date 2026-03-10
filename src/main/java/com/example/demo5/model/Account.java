package com.example.demo5.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Ánh xạ xuống CSDL với tên cột login_name
    @Column(name = "login_name", unique = true, nullable = false)
    private String loginName;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "account_role", 
        joinColumns = @JoinColumn(name = "account_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}