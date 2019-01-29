package com.demo.springbootapis.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springbootapis.repository.UserRepository;
import com.demo.springbootapis.security.JwtTokenProvider;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
//@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
//	@NonNull
//    private AuthenticationManager authenticationManager;
//
//	@NonNull
//    private UserRepository userRepository;
//
//    @NonNull
//    private PasswordEncoder passwordEncoder;
//
//    @NonNull
//    private JwtTokenProvider tokenProvider;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//
//    }
}
