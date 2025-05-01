package com.example.demo.rewiev;

import lombok.Data;

@Data
public class ReviewRequest {
    private String productId;
    private String userId;
    private Integer rating;
    private String comment;
}