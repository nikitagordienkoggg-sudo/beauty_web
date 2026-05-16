package com.example.booking.control.controller;

import com.example.booking.control.dto.request.AuthRequest;
import com.example.booking.control.dto.request.RegisterRequest;
import com.example.booking.control.dto.response.AuthResponse;
import com.example.booking.mediator.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
}
