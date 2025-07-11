package com.urban.backend.dto.response;

import com.urban.backend.enums.PostType;
import com.urban.backend.model.Post;

import java.util.UUID;

public record PostResponse(
        UUID id,
        String title,
        String content,
        PostType postType
) {
    public static PostResponse fromPost(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getPostType()
        );
    }
}
