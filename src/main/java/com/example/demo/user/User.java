package com.example.demo.user;

import com.example.demo.component.BaseMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

// User.java
@Entity
@Table(name = "users")
@Data
public class User extends BaseMapper {

    // ❌ Remove this:
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long UUID;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String role;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}
