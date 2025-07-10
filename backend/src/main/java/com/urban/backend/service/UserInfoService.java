package com.urban.backend.service;

import com.urban.backend.model.UserInfo;
import com.urban.backend.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;

    public UserInfo save(UserInfo userInfo) {
        return userInfoRepository.save(userInfo);
    }

    public UserInfo findById(UUID id) {
        return userInfoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User info not found for ID: " + id));
    }
}
