package com.urban.backend.dto.response;

public record AuthResponse(
        UserResponse user,
        String accessToken,
        String refreshToken,
        long expiresIn
) {
}
