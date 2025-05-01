package com.example.demo.photo;

import lombok.Data;

import java.util.UUID;

@Data
public class PhotoDTO {
    private UUID id;
    private String name;
    private String productId;
    private String createdAt; // ISO format string
    private String updatedAt; // ISO format string
    private Boolean visibility;
}