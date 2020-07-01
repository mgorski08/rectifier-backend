package com.example.rectifierBackend.repository;

import com.example.rectifierBackend.model.Client;
import com.example.rectifierBackend.model.Element;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElementRepository extends JpaRepository<Element, Long> {
    Optional<Element> findById(long id);
    List<Element> findAll();
    List<Element> findByNameContaining(String name);
    List<Element> findByClientId(Long clientId);
    Element save(Element element);
    long deleteById(long id);
}
