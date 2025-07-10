package com.urban.backend.dto.response;

import com.urban.backend.model.User;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        UserInfoResponse userInfo
) {
    public static UserResponse fromUser(User user, UserInfoResponse userInfoResponse) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                userInfoResponse
        );
    }
}
