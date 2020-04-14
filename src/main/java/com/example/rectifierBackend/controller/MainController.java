package com.example.rectifierBackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/")
@RestController
@CrossOrigin
public class MainController {

    @GetMapping("")
    ResponseEntity<?> rootPage(@PathVariable long userId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
