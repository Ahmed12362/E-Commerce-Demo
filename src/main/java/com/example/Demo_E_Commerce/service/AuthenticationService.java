package com.example.Demo_E_Commerce.service;

import com.example.Demo_E_Commerce.config.JwtService;
import com.example.Demo_E_Commerce.model.Role;
import com.example.Demo_E_Commerce.model.Users;
import com.example.Demo_E_Commerce.model.auth.AuthenticationResponse;
import com.example.Demo_E_Commerce.model.auth.LoginRequest;
import com.example.Demo_E_Commerce.model.auth.RegisterRequest;
import com.example.Demo_E_Commerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager manager;
    private final JwtService jwtService;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = new Users();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return new AuthenticationResponse("Register Successfully",user.getRole());
    }

    public AuthenticationResponse login(LoginRequest request) {
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail() , request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow( );
        var token = jwtService.generateToken(user);
        return new AuthenticationResponse(token,user.getRole());
    }
    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        var user = new Users();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        return new AuthenticationResponse("Register Successfully",user.getRole());
    }
}
