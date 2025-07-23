package com.urban.backend.features.userInfo;

import com.urban.backend.features.userInfo.dto.request.UserInfoRequest;
import com.urban.backend.features.userInfo.dto.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/users/info")
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;

    @GetMapping
    public ResponseEntity<UserInfoResponse> getUserInfo(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        UserInfoResponse userInfoResponse = userInfoService.getUserInfo(jwtToken);
        return ResponseEntity.ok(userInfoResponse);
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserInfoResponse> saveUserInfo(
            @RequestHeader("Authorization") String token,
            @ModelAttribute UserInfoRequest request
    ) throws IOException {
        String jwtToken = token.substring(7);
        UserInfoResponse userInfoResponse = userInfoService.saveUserInfo(jwtToken, request);
        return ResponseEntity.ok(userInfoResponse);
    }

    @GetMapping("/{id}/photo")
    public ResponseEntity<ByteArrayResource> getUserPhoto(@PathVariable UUID id) {
        byte[] photoData = userInfoService.getPhotoByInfoId(id);
        if (photoData == null || photoData.length == 0) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayResource resource = new ByteArrayResource(photoData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=photo_" + id + ".jpg")
                .contentType(MediaType.IMAGE_JPEG)
                .contentLength(photoData.length)
                .body(resource);
    }

}
