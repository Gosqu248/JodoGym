package com.urban.backend.controller;

import com.urban.backend.dto.request.UserInfoRequest;
import com.urban.backend.dto.response.UserInfoResponse;
import com.urban.backend.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserInfoService userInfoService;

    @GetMapping(path = "/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        UserInfoResponse userInfoResponse = userInfoService.getUserInfo(jwtToken);
        return ResponseEntity.ok(userInfoResponse);
    }

    @PostMapping(
            path = "/info",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserInfoResponse> saveUserInfo(
            @RequestHeader("Authorization") String token,
            UserInfoRequest request
    ) throws IOException {
        String jwtToken = token.substring(7);
        UserInfoResponse userInfoResponse = userInfoService.saveUserInfo(jwtToken, request);
        return ResponseEntity.ok(userInfoResponse);
    }

}
