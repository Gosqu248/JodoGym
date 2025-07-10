package com.urban.backend.dto.response;

public record RefreshResponse(
        String accessToken,
        String refreshToken,
        long expiresIn
) {
}
