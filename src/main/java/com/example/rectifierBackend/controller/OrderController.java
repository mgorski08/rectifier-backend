package com.example.rectifierBackend.controller;

import com.example.rectifierBackend.message.request.ProcessForm;
import com.example.rectifierBackend.model.Bath;
import com.example.rectifierBackend.model.Order;
import com.example.rectifierBackend.model.Process;
import com.example.rectifierBackend.model.User;
import com.example.rectifierBackend.repository.BathRepository;
import com.example.rectifierBackend.repository.OrderRepository;
import com.example.rectifierBackend.repository.ProcessRepository;
import com.example.rectifierBackend.service.RectifierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.OutputStream;

@RequestMapping("/order")
@RestController
@CrossOrigin
public class OrderController {

    OrderRepository orderRepository;

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
