package com.nutria.nutria_api.auth.controller;

import com.nutria.nutria_api.auth.dto.RegisterUserRequest;
import com.nutria.nutria_api.auth.dto.RegisterUserResponse;
import com.nutria.nutria_api.auth.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(RegisterUserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userRequest));
    }
}
