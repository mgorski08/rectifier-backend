package com.example.rectifierBackend.controller;

import com.example.rectifierBackend.model.Bath;
import com.example.rectifierBackend.model.Process;
import com.example.rectifierBackend.model.User;
import com.example.rectifierBackend.repository.BathRepository;
import com.example.rectifierBackend.repository.ProcessRepository;
import com.example.rectifierBackend.repository.UserRepository;
import com.example.rectifierBackend.service.RectifierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.OutputStream;

@RequestMapping("/bath")
@RestController
@CrossOrigin
public class BathController {
    ProcessRepository processRepository;
    BathRepository bathRepository;
    UserRepository userRepository;
    RectifierService rectifierService;

    public BathController(ProcessRepository processRepository,
                          BathRepository bathRepository,
                          UserRepository userRepository,
                          RectifierService rectifierService) {
        this.processRepository = processRepository;
        this.bathRepository = bathRepository;
        this.userRepository = userRepository;
        this.rectifierService = rectifierService;
    }

    @GetMapping("{bathId}")
    ResponseEntity getOne(@PathVariable long bathId) {
        Bath bath = bathRepository.findById(bathId);
        if(bath == null) {
            return new ResponseEntity<String>("Bath not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Bath>(bath, HttpStatus.OK);
    }

    @GetMapping("")
    ResponseEntity getAll() {
        return new ResponseEntity(bathRepository.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("{bathId}")
    ResponseEntity delete(@PathVariable long bathId) {
        bathRepository.deleteById(bathId);
        return new ResponseEntity<Bath>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("{bathId}/occupy")
    ResponseEntity occupy(@PathVariable long bathId, @RequestBody User userArg) {
        User user = userRepository.findById(userArg.getId());
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

    @PostMapping("{bathId}/start")
    ResponseEntity startProcess(@PathVariable long bathId, @RequestBody Process process) {
        Bath bath = bathRepository.findById(bathId);
        if(bath == null) {
            return new ResponseEntity<String>("Bath not found.", HttpStatus.NOT_FOUND);
        }

        if(process == null) {
            return new ResponseEntity<String>("Couldn't create process.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        processRepository.save(process);
        return new ResponseEntity<Process>(process, HttpStatus.OK);
    }

    @PostMapping("add")
    ResponseEntity add(@RequestBody Bath bath) {
        bathRepository.save(bath);
        return new ResponseEntity<Bath>(bath, HttpStatus.OK);
    }

    @GetMapping(value = "/testStream", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<StreamingResponseBody> testStream() {
        StreamingResponseBody responseBody = (OutputStream stream) -> {
            rectifierService.writeSamples(stream, null);
        };
        return ResponseEntity.ok(responseBody);
    }

}
