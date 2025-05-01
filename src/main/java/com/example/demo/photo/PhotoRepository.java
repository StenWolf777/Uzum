package com.example.demo.photo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PhotoRepository extends JpaRepository<Photo, UUID> {
    List<Photo> findByProductId(String productId);
    List<Photo> findByNameContainingIgnoreCase(String name);
}