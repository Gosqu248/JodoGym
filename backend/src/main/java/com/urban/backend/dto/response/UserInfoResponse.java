package com.urban.backend.dto.response;

import com.urban.backend.model.UserInfo;

import java.time.Instant;

public record UserInfoResponse(
        String firstName,
        String lastName,
        Instant birthDate,
        Instant createdDate,
        byte[] photo
) {
    public static UserInfoResponse fromUserInfo(UserInfo userInfo) {
        return new UserInfoResponse(
                userInfo.getFirstName(),
                userInfo.getLastName(),
                userInfo.getBirthDate(),
                userInfo.getCreatedDate(),
                userInfo.getPhoto()
        );
    }
}
