package com.example.demo.order;


import com.example.demo.cart.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestParam Long userId, @RequestBody List<CartItem> cart) {
        return orderService.createOrder(userId, cart);
    }
}