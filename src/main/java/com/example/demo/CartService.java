package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public Cart addToCart(Long userId, Long productId, Integer quantity) {
        logger.info("Adding to cart: userId={}, productId={}, quantity={}", userId, productId, quantity);

        // Validate inputs
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        // Fetch user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        logger.debug("Fetched user: {}", user.getUsername());

        // Fetch product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
        logger.debug("Fetched product: {}", product.getName());

        // Check product stock
        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock for product: " + product.getName());
        }

        // Find or create cart
        logger.debug("Finding cart for user: {}", user.getUsername());
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    logger.info("No cart found, creating new cart for user: {}", user.getUsername());
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setCreatedAt(java.time.LocalDateTime.now());
                    Cart savedCart = cartRepository.save(newCart);
                    logger.debug("Created and saved new cart with ID: {}", savedCart.getId());
                    return savedCart;
                });


        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseGet(() -> {
                    logger.info("No cart item found for productId: {}, creating new", productId);
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    newItem.setQuantity(0);
                    cart.getItems().add(newItem);
                    return newItem;
                });

        // Update quantity
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItemRepository.save(cartItem);
        logger.debug("Updated cart item: productId={}, quantity={}", productId, cartItem.getQuantity());

        Cart updatedCart = cartRepository.save(cart);
        logger.info("Cart updated successfully for user: {}", user.getUsername());
        return updatedCart;
    }

    public Cart getCart(Long userId) {
        logger.info("Fetching cart for userId: {}", userId);
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + user.getUsername()));
        logger.debug("Fetched cart with ID: {} for user: {}", cart.getId(), user.getUsername());
        return cart;
    }

    public User getUserByUsername(String username) {
        logger.info("Fetching user by username: {}", username);
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        logger.debug("Fetched user: {}", user.getUsername());
        return user;
    }
}