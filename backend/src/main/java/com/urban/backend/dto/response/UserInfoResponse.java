package com.urban.backend.dto.response;

import com.urban.backend.model.UserInfo;

import java.time.Instant;
import java.util.UUID;

public record UserInfoResponse(
        UUID id,
        String firstName,
        String lastName,
        Instant birthDate,
        Instant createdDate
) {
    public static UserInfoResponse fromUserInfo(UserInfo userInfo) {
        if (userInfo == null) {
            throw new IllegalArgumentException("userInfo nie może być null");
        }

        return new UserInfoResponse(
                userInfo.getId(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getBirthDate(),
                userInfo.getCreatedDate()
        );
    }
}
