package com.example.demo.order;

import com.example.demo.component.BaseMapper;
import com.example.demo.product.Product;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_items")
@Data
public class OrderItem extends BaseMapper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UUID;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Double priceAtPurchase;
}