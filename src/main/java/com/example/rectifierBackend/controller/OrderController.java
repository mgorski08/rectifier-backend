package com.example.rectifierBackend.controller;

import com.example.rectifierBackend.model.Order;
import com.example.rectifierBackend.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/order")
@RestController
@CrossOrigin
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("{orderId}")
    ResponseEntity<?> getOne(@PathVariable long orderId) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found.")
                );
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("{orderId}")
    ResponseEntity<?> delete(@PathVariable long orderId) {
        orderRepository.deleteById(orderId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
    ResponseEntity<?> add(@RequestBody Order order) {
        orderRepository.save(order);
        return ResponseEntity.ok(order);
    }

    @GetMapping("")
    ResponseEntity<?> getAll() {
        return ResponseEntity.ok(orderRepository.findAll());
    }
}
