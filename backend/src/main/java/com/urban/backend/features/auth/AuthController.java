package com.urban.backend.features.auth;

import com.urban.backend.features.auth.dto.response.AuthResponse;
import com.urban.backend.features.auth.dto.response.RefreshResponse;
import com.urban.backend.shared.dto.response.ResultResponse;
import com.urban.backend.features.user.dto.response.UserResponse;
import com.urban.backend.features.auth.dto.request.LoginRequest;
import com.urban.backend.features.auth.dto.request.RegisterRequest;
import com.urban.backend.features.auth.dto.request.ResetPasswordRequest;
import com.urban.backend.features.resetCode.dto.request.ResetCodeRequest;
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
            @RequestHeader("Authorization") String token
    ) {
        String refreshToken = token.substring(7);
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
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
