package com.urban.backend.features.auth.dto.response;

public record RefreshResponse(
        String accessToken,
        String refreshToken,
        long expiresIn
) {
}
