package com.example.demo.order;

import com.example.demo.cart.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Long userId, List<CartItem> cart) {
        // Calculate total price
        double totalPrice = cart.stream().mapToDouble(cartItem -> cartItem.getPrice()).sum();
        Order order = new Order(userId, cart);
        // Save order to database
        return orderRepository.save(order);
    }
}