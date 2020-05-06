package com.example.rectifierBackend.controller;

import com.example.rectifierBackend.model.User;
import com.example.rectifierBackend.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/user")
@RestController
@CrossOrigin
public class UserController {

    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    ResponseEntity<?> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("{userId}")
    ResponseEntity<?> getOne(@PathVariable long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")
                );
        return ResponseEntity.ok(user);
    }

    @GetMapping("/current")
    ResponseEntity<?> getCurrent() {
        return ResponseEntity.ok(User.getCurrentUser().orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED)
        ));
    }

    @DeleteMapping("{userId}")
    ResponseEntity<?> delete(@PathVariable long userId) {
        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }
}
