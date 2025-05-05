package com.example.demo.cart;


import com.example.demo.component.BaseMapper;
import com.example.demo.product.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "cart_items")
@Data
public class CartItem extends BaseMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UUID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false, insertable = false)
    private Cart cart;

    @Column(name = "cart_id")
    private UUID cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, insertable = false)
    private Product product;

    @Column(name = "product_id")
    private UUID productId;


    @Column(nullable = false)
    private Integer quantity;

    public double getPrice() {
        return 0;
    }
}