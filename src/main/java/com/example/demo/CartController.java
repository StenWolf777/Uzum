package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestParam Long productId, @RequestParam Integer quantity,
                                          Authentication authentication) {
        logger.info("POST /api/cart/add: productId={}, quantity={}", productId, quantity);
        if (authentication == null || authentication.getName() == null) {
            logger.error("Authentication is null or no username found");
            throw new RuntimeException("User not authenticated");
        }
        String username = authentication.getName();
        logger.debug("Authenticated user: {}", username);
        User user = cartService.getUserByUsername(username);
        Long userId = user.getId();
        Cart cart = cartService.addToCart(userId, productId, quantity);
        logger.info("Added to cart successfully for user: {}", username);
        return ResponseEntity.ok(cart);
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(Authentication authentication) {
        logger.info("GET /api/cart");
        if (authentication == null || authentication.getName() == null) {
            logger.error("Authentication is null or no username found");
            throw new RuntimeException("User not authenticated");
        }
        String username = authentication.getName();
        logger.debug("Authenticated user: {}", username);
        User user = cartService.getUserByUsername(username);
        Long userId = user.getId();
        Cart cart = cartService.getCart(userId);
        logger.info("Fetched cart successfully for user: {}", username);
        return ResponseEntity.ok(cart);
    }
}