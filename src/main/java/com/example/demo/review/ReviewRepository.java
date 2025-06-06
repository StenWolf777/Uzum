package com.example.demo.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {
    Optional<ReviewEntity> findByUserIdAndVisibilityTrue(UUID userId);

    List<ReviewEntity> findAllByProductId(UUID productId);

    Optional<Object> findByProductIdAndUserId(UUID productId, UUID userId);

    List<ReviewEntity> findAllByProductIdAndVisibilityTrue(UUID productId);

    Optional<ReviewEntity> findByIdAndVisibilityTrue(UUID id);

    Optional<ReviewEntity> findByUserIdAndProductIdAndVisibilityTrue(UUID userId, UUID productID);
}
