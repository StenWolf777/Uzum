package com.example.demo.review;


import com.example.demo.component.BaseMapper;
import com.example.demo.product.Product;
import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Check(constraints = "rating BETWEEN 1 AND 5")
public class ReviewEntity extends BaseMapper {


    private int rating;
    private String comment;

    public ReviewEntity(int rating, String comment, UUID productId, UUID userId) {
        this.rating = rating;
        this.comment = comment;
        this.productId = productId;
        this.userId = userId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",
            insertable = false,
            updatable = false)
    private Product product;

    @Column(name = "product_id")
    private UUID productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            insertable = false,
            updatable = false)
    private User user;

    @Column(name = "user_id")
    private UUID userId;
}
