package com.example.rectifierBackend.controller;

import com.example.rectifierBackend.message.response.ResponseMessage;
import com.example.rectifierBackend.model.User;
import com.example.rectifierBackend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
@CrossOrigin
public class UserController {

    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("{userId}")
    ResponseEntity<?> getOne(@PathVariable long userId) {
        User user = userRepository.findById(userId);
        if(user == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ResponseMessage("User not found."));
        }
        return ResponseEntity.ok(user);
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

    @DeleteMapping("{userId}")
    ResponseEntity<?> delete(@PathVariable long userId) {
        userRepository.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}
