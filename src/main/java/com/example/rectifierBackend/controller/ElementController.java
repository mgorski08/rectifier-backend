package com.example.rectifierBackend.controller;

import com.example.rectifierBackend.model.Element;
import com.example.rectifierBackend.repository.ElementRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/element")
@RestController
@CrossOrigin
public class ElementController {

    ElementRepository elementRepository;

    public ElementController(ElementRepository elementRepository) {
        this.elementRepository = elementRepository;
    }

    @GetMapping("")
    ResponseEntity<?> getAll() {
        return ResponseEntity.ok(elementRepository.findAll());
    }

    @GetMapping("{elementId}")
    ResponseEntity<?> getOne(@PathVariable long elementId) {
        Element element = elementRepository
                .findById(elementId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Element not found.")
                );
        return ResponseEntity.ok(element);
    }

    @DeleteMapping("{elementId}")
    ResponseEntity<?> delete(@PathVariable long elementId) {
        try {
            elementRepository.deleteById(elementId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("add")
    ResponseEntity<?> add(@RequestBody Element element) {
        elementRepository.save(element);
        return ResponseEntity.ok(element);
    }

}
