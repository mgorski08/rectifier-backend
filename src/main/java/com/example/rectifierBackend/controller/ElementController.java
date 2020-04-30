package com.example.rectifierBackend.controller;

import com.example.rectifierBackend.message.response.ResponseMessage;
import com.example.rectifierBackend.model.Element;
import com.example.rectifierBackend.repository.ElementRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Element not found."
                        )
                );
        return ResponseEntity.ok(element);
    }

    @GetMapping("/current")
    ResponseEntity<?> getCurrent() {
        return ResponseEntity
                .ok(SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal()
                );
    }

    @DeleteMapping("{elementId}")
    ResponseEntity<?> delete(@PathVariable long elementId) {
        elementRepository.deleteById(elementId);
        return ResponseEntity.noContent().build();
    }
}
