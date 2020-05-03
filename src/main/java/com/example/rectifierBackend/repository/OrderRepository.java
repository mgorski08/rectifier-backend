package com.example.rectifierBackend.repository;

import com.example.rectifierBackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(long id);
    List<Order> findAll();
    Order save(Order sample);
    long deleteById(long id);
}
