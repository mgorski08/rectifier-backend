package com.example.rectifierBackend.controller;

import com.example.rectifierBackend.model.Bath;
import com.example.rectifierBackend.model.Process;
import com.example.rectifierBackend.model.User;
import com.example.rectifierBackend.repository.BathRepository;
import com.example.rectifierBackend.repository.ProcessRepository;
import com.example.rectifierBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
@CrossOrigin
public class UserController {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    ResponseEntity<?> getOne(@PathVariable long userId) {
        User user = userRepository.findById(userId);
        if(user == null) {
            return new ResponseEntity<String>("User not found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("/current")
    ResponseEntity<?> getCurrent() {
        return new ResponseEntity<>(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), HttpStatus.OK);
    }

    @RequestMapping(value = "{userId}", method = RequestMethod.DELETE)
    ResponseEntity<?> delete(@PathVariable long userId) {
        userRepository.deleteById(userId);
        return new ResponseEntity<Bath>(HttpStatus.NO_CONTENT);
    }
}
