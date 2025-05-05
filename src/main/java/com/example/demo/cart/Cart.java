package com.example.demo.cart;

import com.example.demo.component.BaseMapper;
import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "carts")
@Data
public class Cart extends BaseMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UUID ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, insertable = false)
    private User user;


    @Column(name = "user_id")
    private java.util.UUID userId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
}