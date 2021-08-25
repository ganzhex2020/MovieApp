package com.phi.moviecloud.auth.controller;

import com.phi.moviecloud.auth.dto.ApiResponse;
import com.phi.moviecloud.auth.dto.JwtAuthenticationResponse;
import com.phi.moviecloud.auth.dto.LoginRequest;
import com.phi.moviecloud.auth.dto.SignUpRequest;
import com.phi.moviecloud.auth.security.JwtTokenProvider;
import com.phi.moviecloud.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthService authService;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(JwtAuthenticationResponse.builder().accessToken(jwt).build());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (authService.usernameExists(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
        }

        authService.SaveUser(
                signUpRequest.getUsername(),
                signUpRequest.getPassword(),
                signUpRequest.isAdmin()
                );

        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
    }
}
