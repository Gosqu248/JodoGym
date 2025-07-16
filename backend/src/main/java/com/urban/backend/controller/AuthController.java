package com.urban.backend.controller;

import com.urban.backend.dto.request.*;
import com.urban.backend.dto.response.AuthResponse;
import com.urban.backend.dto.response.RefreshResponse;
import com.urban.backend.dto.response.ResultResponse;
import com.urban.backend.dto.response.UserResponse;
import com.urban.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(
            @Valid @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request
    ) {
        return ResponseEntity.ok(authService.refreshToken(request.refreshToken()));
    }

    @PostMapping("/send-reset-mail/{email}")
    public ResponseEntity<ResultResponse> sendResetPasswordEmail(
            @PathVariable String email
    )  {
        return ResponseEntity.ok(authService.sendResetPasswordEmail(email));
    }

    @PostMapping("/verify-reset-code")
    public ResponseEntity<ResultResponse> verifyResetCode(
            @RequestBody @Valid ResetCodeRequest request
    ) {
        return ResponseEntity.ok(authService.verifyResetCode(request));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResultResponse> resetPassword(
            @RequestBody @Valid ResetPasswordRequest request
    ) {
        return ResponseEntity.ok(authService.resetPassword(request));
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateToken(
            @RequestHeader("Authorization") String token
    ) {
        String jwtToken = token.substring(7);
        boolean isValid = authService.validateToken(jwtToken);
        return ResponseEntity.ok(isValid);
    }
}
