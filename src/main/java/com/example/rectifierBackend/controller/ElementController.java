package com.example.rectifierBackend.controller;

import com.example.rectifierBackend.model.Client;
import com.example.rectifierBackend.model.Element;
import com.example.rectifierBackend.repository.ElementRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/element")
@RestController
@CrossOrigin
public class ElementController {

    private final ElementRepository elementRepository;

    public ElementController(ElementRepository elementRepository) {
        this.elementRepository = elementRepository;
    }

    @GetMapping("")
    ResponseEntity<?> getAll() {
        return ResponseEntity.ok(elementRepository.findAll());
    }

    @GetMapping("justNames")
    ResponseEntity<?> getNames() {
        List<Element> originalList = elementRepository.findAll();
        List<String> result = new ArrayList<>(originalList.size());
        for(Element element : originalList) {
            result.add(element.getName());
        }
        return ResponseEntity.ok(result);
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

    @GetMapping("byName/{elementName}")
    ResponseEntity<?> getByName(@PathVariable String elementName) {
        return ResponseEntity.ok(elementRepository.findByNameContaining(elementName));
    }

    @GetMapping("byClientId/{clientId}")
    ResponseEntity<?> getByClientId(@PathVariable long clientId) {
        return ResponseEntity.ok(elementRepository.findByClientId(clientId));
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

    @PostMapping("")
    ResponseEntity<?> add(@RequestBody Element element) {
        elementRepository.save(element);
        return ResponseEntity.ok(element);
    }

}
