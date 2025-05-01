package com.example.demo.rewiev;

import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;
import com.example.demo.rewiev.Review;
import com.example.demo.rewiev.ReviewRepository;
import com.example.demo.rewiev.ReviewRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    @PreAuthorize("isAuthenticated()")
    @Transactional
    public Review createReview(ReviewRequest request, String userId) {
        // Validate rating
        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        // Check if user already reviewed this product
        if (reviewRepository.existsByUserIdAndProductId(userId, request.getProductId())) {
            throw new IllegalStateException("User already reviewed this product");
        }

        // Create review
        Review review = new Review();
        review.setProductId(request.getProductId());
        review.setUserId(userId);
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        review = reviewRepository.save(review);

        // Update product rating
        updateProductRating(request.getProductId());

        return review;
    }

    @PreAuthorize("isAuthenticated()")
    public Review getReview(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
    }

    @PreAuthorize("isAuthenticated()")
    public List<Review> getReviewsByProductId(String productId) {
        return reviewRepository.findByProductId(productId);
    }

    @PreAuthorize("isAuthenticated() and #request.userId == authentication.principal.username")
    @Transactional
    public Review updateReview(Long id, ReviewRequest request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        if (request.getRating() < 1 || request.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        review.setRating(request.getRating());
        review.setComment(request.getComment());

        return reviewRepository.save(review); // No product rating update
    }

    @PreAuthorize("isAuthenticated() and #userId == authentication.principal.username")
    @Transactional
    public void deleteReview(Long id, String userId) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        reviewRepository.delete(review); // No product rating update
    }

    private void updateProductRating(String productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        Optional<Product> productOpt = productRepository.findById(Long.valueOf(productId));

        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            if (reviews.isEmpty()) {
                product.setRating(0.0);
            } else {
                double average = reviews.stream()
                        .mapToInt(Review::getRating)
                        .average()
                        .orElse(0.0);
                // Round to 0.1 precision
                BigDecimal rounded = BigDecimal.valueOf(average)
                        .setScale(1, RoundingMode.HALF_UP);
                product.setRating(rounded.doubleValue());
            }
            productRepository.save(product);
        }
    }
}