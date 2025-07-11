package com.urban.backend.service;

import com.urban.backend.dto.request.PostRequest;
import com.urban.backend.dto.response.PageResponse;
import com.urban.backend.dto.response.PostResponse;
import com.urban.backend.model.Post;
import com.urban.backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PageResponse<PostResponse> searchPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);

        Page<PostResponse> mappedPage = postPage.map(PostResponse::fromPost);

        return PageResponse.from(mappedPage);
    }

    public byte[] getPostPhoto(UUID postId) {
        Post post = findPostById(postId);

        if (post.getPhoto() == null) {
            throw new IllegalArgumentException("Post does not have a photo");
        }

        return post.getPhoto();
    }

    public PostResponse createPost(PostRequest request) {
        try {
            Post post = toPost(request);
            Post savedPost = postRepository.save(post);

            return PostResponse.fromPost(savedPost);
        } catch (IOException e) {
            throw new RuntimeException("Error processing post data", e);
        }
    }


    private Post findPostById(UUID postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));
    }


    private Post toPost(PostRequest request) throws IOException {
        return Post.builder()
                .title(request.title())
                .content(request.content())
                .postType(request.postType())
                .photo(request.photo() != null
                        ? request.photo().getBytes()
                        : null)
                .build();
    }
}
