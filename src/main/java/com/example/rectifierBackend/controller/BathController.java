package com.example.rectifierBackend.controller;

import com.example.rectifierBackend.model.Bath;
import com.example.rectifierBackend.model.Process;
import com.example.rectifierBackend.model.User;
import com.example.rectifierBackend.rectifier.RectifierService;
import com.example.rectifierBackend.repository.BathRepository;
import com.example.rectifierBackend.repository.ProcessRepository;
import com.example.rectifierBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/bath")
@RestController
public class BathController {

    @Autowired
    ProcessRepository processRepository;

    @Autowired
    BathRepository bathRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RectifierService rectifierService;

    @RequestMapping(value = "{bathId}", method = RequestMethod.GET)
    ResponseEntity getOne(@PathVariable long bathId) {
        Bath bath = bathRepository.findById(bathId);
        if(bath == null) {
            return new ResponseEntity<String>("Bath not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Bath>(bath, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    ResponseEntity getAll() {
        return new ResponseEntity(bathRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "{bathId}", method = RequestMethod.DELETE)
    ResponseEntity delete(@PathVariable long bathId) {
        bathRepository.deleteById(bathId);
        return new ResponseEntity<Bath>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "{bathId}/occupy", method = RequestMethod.POST)
    ResponseEntity occupy(@PathVariable long bathId, @RequestParam long userId) {
        User user = userRepository.findById(userId);
        Bath bath = bathRepository.findById(bathId);
        if(bath == null) {
            return new ResponseEntity<String>("Bath not found.", HttpStatus.NOT_FOUND);
        }
        if(user == null) {
            return new ResponseEntity<String>("User not found.", HttpStatus.NOT_FOUND);
        }
        if(bath.getUser() != null && bath.getUser().getId() != user.getId()) {
            return new ResponseEntity<String>("Bath already occupied by a different user.", HttpStatus.FORBIDDEN);
        }
        bath.setUser(user);
        bathRepository.save(bath);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "{bathId}/start", method = RequestMethod.POST)
    ResponseEntity startProcess(@PathVariable long bathId, @RequestBody Process process) {
        Bath bath = bathRepository.findById(bathId);
        if(bath == null) {
            return new ResponseEntity<String>("Bath not found.", HttpStatus.NOT_FOUND);
        }
        RectifierService.startProcess(process, bath);

        if(process == null) {
            return new ResponseEntity<String>("Couldn't create process.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        processRepository.save(process);
        return new ResponseEntity<Process>(process, HttpStatus.OK);
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    ResponseEntity add(@RequestBody Bath bath) {
        bathRepository.save(bath);
        return new ResponseEntity<Bath>(bath, HttpStatus.OK);
    }

}
