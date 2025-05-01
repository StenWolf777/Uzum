package com.example.demo.photo;

import com.example.demo.component.BaseMapper;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
public class Photo extends BaseMapper {
    private String name;



        @Column(name = "product_id") // Maps to the product_id column in the photo table
        private String productId;

        // Other fields and methods
    }