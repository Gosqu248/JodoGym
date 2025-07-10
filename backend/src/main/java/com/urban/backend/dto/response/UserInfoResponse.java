package com.urban.backend.dto.response;

import com.urban.backend.model.UserInfo;

import java.time.Instant;

public record UserInfoResponse(
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
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getBirthDate(),
                userInfo.getCreatedDate()
        );
    }
}
