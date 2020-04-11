package com.example.rectifierBackend.controller;

import com.example.rectifierBackend.model.Bath;
import com.example.rectifierBackend.model.Client;
import com.example.rectifierBackend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/client")
@RestController
@CrossOrigin
public class ClientController {
    @Autowired
    ClientRepository clientRepository;

    @RequestMapping(value = "{clientId}", method = RequestMethod.GET)
    ResponseEntity getOne(@PathVariable long clientId) {
        Client client = clientRepository.findById(clientId);
        if(client == null) {
            return new ResponseEntity<String>("Client not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }

    @RequestMapping(value = "{clientId}", method = RequestMethod.DELETE)
    ResponseEntity delete(@PathVariable long clientId) {
        clientRepository.deleteById(clientId);
        return new ResponseEntity<Bath>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    ResponseEntity add(@RequestBody Client client) {
        clientRepository.save(client);
        return new ResponseEntity<Client>(client, HttpStatus.OK);
    }

}