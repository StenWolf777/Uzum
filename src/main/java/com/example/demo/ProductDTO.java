package com.example.demo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductDTO {
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Positive(message = "Price must be positive")
    private Double price;

    @Positive(message = "Stock must be positive")
    private Integer stock;
}