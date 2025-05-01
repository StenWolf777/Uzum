package com.example.demo.request;

import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class PriceUpdateRequest {

    @Positive(message = "Price must be positive")
    private BigDecimal price;

    // Getter and Setter
    public @Positive(message = "Price must be positive") BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}