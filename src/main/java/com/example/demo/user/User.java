package com.example.demo.user;

import com.example.demo.component.BaseMapper;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User extends BaseMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UUID ;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String role; // USER, ADMIN

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}