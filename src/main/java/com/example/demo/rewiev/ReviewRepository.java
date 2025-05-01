package com.example.demo.rewiev;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProductId(String productId);
    boolean existsByUserIdAndProductId(String userId, String productId);
}