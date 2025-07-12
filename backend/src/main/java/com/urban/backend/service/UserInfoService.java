package com.urban.backend.service;

import com.urban.backend.dto.request.UserInfoRequest;
import com.urban.backend.dto.response.UserInfoResponse;
import com.urban.backend.model.User;
import com.urban.backend.model.UserInfo;
import com.urban.backend.repository.UserInfoRepository;
import com.urban.backend.sercurity.jwt.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final JwtService jwtService;
    private final UserInfoRepository userInfoRepository;
    private final UserService userService;
    private final MembershipService membershipService;

    @Transactional
    public UserInfoResponse saveUserInfo(String token, UserInfoRequest request) throws IOException {
        String email = jwtService.extractUsername(token);
        User user = userService.findByEmail(email);

        UserInfo userInfo;

        if (userInfoRepository.existsByUserId(user.getId())) {
            userInfo = findByUserId(user.getId());
            userInfo.setFirstName(request.firstName());
            userInfo.setLastName(request.lastName());
            userInfo.setBirthDate(request.birthDate());
            if (request.photo() != null && !request.photo().isEmpty()) {
                userInfo.setPhoto(request.photo().getBytes());
            }
        } else {
            userInfo = UserInfo.builder()
                    .user(user)
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .birthDate(request.birthDate())
                    .photo(request.photo() != null ? request.photo().getBytes() : null)
                    .build();

            user.setFirstLogin(false);
            userService.save(user);
        }

        membershipService.createMembershipForUser(user);
        userInfo = userInfoRepository.save(userInfo);

        return UserInfoResponse.fromUserInfo(userInfo);
    }

    @Transactional
    public UserInfoResponse getUserInfo(String jwtToken) {
        String email = jwtService.extractUsername(jwtToken);
        User user = userService.findByEmail(email);

        UserInfo userInfo = user.getUserInfo();

        return UserInfoResponse.fromUserInfo(userInfo);
    }

    @Transactional
    public byte[] getPhotoByInfoId(UUID id) {
        UserInfo userInfo = findById(id);
        return userInfo.getPhoto();
    }

    @Transactional
    public UserInfo findById(UUID id) {
        return userInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User info not found for ID: " + id));
    }

    @Transactional
    public UserInfo findByUserId(UUID userId) {
        return userInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User info not found for user ID: " + userId));
    }
}
