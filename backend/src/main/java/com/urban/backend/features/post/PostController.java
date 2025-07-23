package com.urban.backend.features.post;

import com.urban.backend.features.post.dto.request.PostRequest;
import com.urban.backend.shared.dto.response.PageResponse;
import com.urban.backend.features.post.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<PageResponse<PostResponse>> searchPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));

        PageResponse<PostResponse> response = postService.searchPosts(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/photo/{postId}")
    public ResponseEntity<ByteArrayResource> getPostPhoto(@PathVariable UUID postId) {
        byte[] photoData = postService.getPostPhoto(postId);
        ByteArrayResource resource = new ByteArrayResource(photoData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=post_" + postId + ".jpg")
                .contentType(MediaType.IMAGE_JPEG)
                .contentLength(photoData.length)
                .body(resource);
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PostResponse> createPost(@ModelAttribute PostRequest postRequest) {
        PostResponse createdPost = postService.createPost(postRequest);
        return ResponseEntity.status(201).body(createdPost);
    }
}
