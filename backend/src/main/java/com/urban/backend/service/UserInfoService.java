package com.urban.backend.service;

import com.urban.backend.dto.request.UserInfoRequest;
import com.urban.backend.dto.response.UserInfoResponse;
import com.urban.backend.model.User;
import com.urban.backend.model.UserInfo;
import com.urban.backend.repository.UserInfoRepository;
import com.urban.backend.repository.UserRepository;
import com.urban.backend.sercurity.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserInfoRepository userInfoRepository;
    private final UserService userService;

    public UserInfoResponse saveUserInfo(String token, UserInfoRequest request) throws IOException {
        String email = jwtService.extractUsername(token);

        User user = userService.findByEmail(email);

        UserInfo userInfo = UserInfo.builder()
                .user(user)
                .firstName(request.firstName())
                .lastName(request.lastName())
                .birthDate(request.birthDate())
                .photo(request.photo() != null ? request.photo().getBytes() : null)
                .build();

        userInfo = userInfoRepository.save(userInfo);

        return UserInfoResponse.fromUserInfo(userInfo);
    }

    public UserInfo findById(UUID id) {
        return userInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User info not found for ID: " + id));
    }
}
