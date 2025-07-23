package com.urban.backend.features.auth.dto.response;

import com.urban.backend.features.user.dto.response.UserResponse;

public record AuthResponse(
        UserResponse user,
        String accessToken,
        String refreshToken,
        long expiresIn
) {
}
