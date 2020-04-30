package com.example.rectifierBackend.controller;

import com.example.rectifierBackend.message.request.LoginForm;
import com.example.rectifierBackend.message.request.SignUpForm;
import com.example.rectifierBackend.message.response.JwtResponse;
import com.example.rectifierBackend.message.response.ResponseMessage;
import com.example.rectifierBackend.model.User;
import com.example.rectifierBackend.repository.UserRepository;
import com.example.rectifierBackend.security.jwt.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Set;

@RequestMapping("/auth")
@RestController
@CrossOrigin
public class AuthController {
    private static final long JWT_EXPIRATION = 3600000; //milliseconds

    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    JwtProvider jwtProvider;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername()
                        , loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        long iss = new Date().getTime();
        long exp = iss + JWT_EXPIRATION;

        String jwt = jwtProvider.generateJwtToken(authentication, iss, exp);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), exp, userDetails.getAuthorities()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseMessage("Fail -> Username is already taken."));
        }
        // Create user account
        User user = new User(signUpRequest.getUsername(), passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();

        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setRoles(strRoles);
        userRepository.save(user);

        return new ResponseEntity<>(new ResponseMessage("User registered successfully."), HttpStatus.OK);

    }

}
