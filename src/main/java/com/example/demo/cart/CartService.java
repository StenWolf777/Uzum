package com.example.demo.cart;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    public Cart addToCart(UUID userId, Long productId, Integer quantity) {
        logger.info("Adding to cart: userId={}, productId={}, quantity={}", userId, productId, quantity);

        if (userId == null) throw new IllegalArgumentException("User ID cannot be null");
        if (productId == null) throw new IllegalArgumentException("Product ID cannot be null");
        if (quantity == null || quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");

        User user = (User) userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));

        if (product.getStock()) {
            throw new RuntimeException("Insufficient stock for product: " + product.getName());
        }

        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            newCart.setCreatedAt(java.time.LocalDateTime.now());
            return cartRepository.save(newCart);
        });

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    newItem.setQuantity(0);
                    cart.getItems().add(newItem);
                    return newItem;
                });

        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItemRepository.save(cartItem);

        return cartRepository.save(cart);
    }

    public Cart getCart(UUID userId) {
        logger.info("Fetching cart for userId: {}", userId);
        if (userId == null) throw new IllegalArgumentException("User ID cannot be null");
        User user = (User) userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + user.getUsername()));
    }

    public User getUserByUsername(String username) {
        logger.info("Fetching user by username: {}", username);
        if (username == null || username.trim().isEmpty())
            throw new IllegalArgumentException("Username cannot be null or empty");
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }


    public Page<CartItem> getCartItemsPaged(Long userId, int page, int size) {
        logger.info("Fetching paginated cart items for userId: {}", userId);
        if (userId == null) throw new IllegalArgumentException("User ID cannot be null");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + user.getUsername()));

        Pageable pageable = PageRequest.of(page, size);
        return cartItemRepository.findAllByCart(cart, pageable);
    }
}
