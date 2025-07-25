package com.urban.backend.features.post.dto.response;

import com.urban.backend.enums.PostType;
import com.urban.backend.features.post.Post;

import java.time.Instant;
import java.util.UUID;

public record PostResponse(
        UUID id,
        String title,
        String content,
        PostType postType,
        Instant createdDate
) {
    public static PostResponse fromPost(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getPostType(),
                post.getCreatedDate()
        );
    }
}
