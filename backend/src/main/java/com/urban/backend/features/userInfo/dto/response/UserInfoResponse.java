package com.urban.backend.features.userInfo.dto.response;

import com.urban.backend.features.userInfo.UserInfo;

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
        return new UserInfoResponse(
                userInfo.getId(),
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getBirthDate(),
                userInfo.getCreatedDate()
        );
    }
}
