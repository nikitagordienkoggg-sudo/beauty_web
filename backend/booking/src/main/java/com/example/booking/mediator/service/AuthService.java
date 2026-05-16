package com.example.booking.mediator.service;

import com.example.booking.control.dto.request.AuthRequest;
import com.example.booking.control.dto.request.RegisterRequest;
import com.example.booking.control.dto.response.AuthResponse;
import com.example.booking.entity.Client;
import com.example.booking.entity.Master;
import com.example.booking.entity.Role;
import com.example.booking.entity.User;
import com.example.booking.foundation.repository.IUserRepository;
import com.example.booking.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(IUserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        Role role = Role.valueOf(request.role);
        User user;
        if (role == Role.ROLE_MASTER) {
            Master master = new Master();
            master.setSpecialization("GENERAL");
            user = master;
        } else {
            user = new Client();
        }

        user.setName(request.name);
        user.setEmail(request.email);
        user.setPhone(request.phone);
        user.setPassword(passwordEncoder.encode(request.password));
        user.setRole(role);

        userRepository.save(user);
        String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email, request.password));
        User user = userRepository.findByEmail(request.email).orElseThrow();
        return new AuthResponse(jwtService.generateToken(user.getEmail(), user.getRole().name()));
    }
}
