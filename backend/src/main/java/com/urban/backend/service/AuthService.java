package com.urban.backend.service;

import com.urban.backend.dto.request.LoginRequest;
import com.urban.backend.dto.request.RegisterRequest;
import com.urban.backend.dto.response.AuthResponse;
import com.urban.backend.dto.response.RefreshResponse;
import com.urban.backend.dto.response.UserResponse;
import com.urban.backend.enums.Role;
import com.urban.backend.model.User;
import com.urban.backend.sercurity.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserResponse register(RegisterRequest request) {
        if (userService.existsByEmail(request.email())) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        var user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .isFirstLogin(true)
                .role(Role.USER)
                .build();

        var savedUser = userService.save(user);
        log.info("User registered successfully: {}", savedUser.getEmail());
        return UserResponse.fromUser(savedUser);
    }

    public AuthResponse authenticate(LoginRequest request) {
        log.info("Attempting to authenticate user: {}", request.email());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
            log.info("Authentication successful for user: {}", request.email());
        } catch (Exception e) {
            log.error("Authentication failed for user: {}", request.email(), e);
            throw e;
        }

        var user = userService.findByEmail(request.email());
        log.info("User found: {}, isFirstLogin: {}", user.getEmail(), user.isFirstLogin());

        UserResponse userResponse = UserResponse.fromUser(user);

        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        var expiresIn = jwtService.getExpirationTime(accessToken);

        return new AuthResponse(
                userResponse,
                accessToken,
                refreshToken,
                expiresIn
        );
    }

    public RefreshResponse refreshToken(String refreshToken) {
        final String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            var user = userService.findByEmail(userEmail);

            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                var refreshTokenNew = jwtService.generateRefreshToken(user);
                var expiresIn = jwtService.getExpirationTime(accessToken);

                return new RefreshResponse(
                        accessToken,
                        refreshTokenNew,
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
