package com.urban.backend.service;

import com.urban.backend.dto.request.LoginRequest;
import com.urban.backend.dto.request.RegisterRequest;
import com.urban.backend.dto.response.AuthResponse;
import com.urban.backend.enums.Role;
import com.urban.backend.model.User;
import com.urban.backend.repository.UserRepository;
import com.urban.backend.sercurity.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        var user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .build();

        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(user);
        var expiresIn = jwtService.getExpirationTime(jwtToken);

        return new AuthResponse(
                jwtToken,
                refreshToken,
                expiresIn
        );
    }

    public AuthResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        var expiresIn = jwtService.getExpirationTime(jwtToken);

        return new AuthResponse(
                jwtToken,
                refreshToken,
                expiresIn
        );
    }

    public AuthResponse refreshToken(String refreshToken) {
        final String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            var user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                var newRefreshToken = jwtService.generateRefreshToken(user);
                var expiresIn = jwtService.getExpirationTime(accessToken);


                return new AuthResponse(
                        accessToken,
                        newRefreshToken,
                        expiresIn
                );
            }
        }

        throw new RuntimeException("Invalid refresh token");
    }

    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }
}
