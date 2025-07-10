package com.urban.backend.dto.response;

import com.urban.backend.model.User;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        boolean isFirstLogin
) {
    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.isFirstLogin()
        );
    }
}
