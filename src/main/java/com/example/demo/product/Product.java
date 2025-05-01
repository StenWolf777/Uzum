package com.example.demo.product;

import com.example.demo.category.Category;
import com.example.demo.component.BaseMapper;
import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseMapper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UUID;

    private String name;
    private Double price;
    private Double rating;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User seller;

    public boolean getStock() {
        return false;
    }
}
