package com.example.rectifierBackend.controller;

import com.example.rectifierBackend.model.Client;
import com.example.rectifierBackend.repository.ClientRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/client")
@RestController
@CrossOrigin
public class ClientController {

    private final ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("")
    ResponseEntity<?> getAll() {
        return ResponseEntity.ok(clientRepository.findAll());
    }

    @GetMapping("{clientId}")
    ResponseEntity<?> getOne(@PathVariable long clientId) {
        Client client = clientRepository
                .findById(clientId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found.")
                );
        return ResponseEntity.ok(client);
    }

    @DeleteMapping("{clientId}")
    ResponseEntity<?> delete(@PathVariable long clientId) {
        try {
            clientRepository.deleteById(clientId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("")
    ResponseEntity<?> add(@RequestBody Client client) {
        clientRepository.save(client);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

}
