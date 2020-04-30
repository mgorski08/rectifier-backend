package com.example.rectifierBackend.controller;

import com.example.rectifierBackend.message.response.ResponseMessage;
import com.example.rectifierBackend.model.Client;
import com.example.rectifierBackend.repository.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/client")
@RestController
@CrossOrigin
public class ClientController {

    ClientRepository clientRepository;

    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("")
    ResponseEntity<?> getAll() {
        return ResponseEntity.ok(clientRepository.findAll());
    }

    @GetMapping("{clientId}")
    ResponseEntity<?> getOne(@PathVariable long clientId) {
        Client client = clientRepository.findById(clientId);
        if(client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("Client not found."));
        }
        return ResponseEntity.ok(client);
    }

    @DeleteMapping("{clientId}")
    ResponseEntity<?> delete(@PathVariable long clientId) {
        clientRepository.deleteById(clientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("add")
    ResponseEntity<?> add(@RequestBody Client client) {
        clientRepository.save(client);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

}
