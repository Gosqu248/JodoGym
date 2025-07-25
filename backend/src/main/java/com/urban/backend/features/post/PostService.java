package com.urban.backend.features.post;

import com.urban.backend.features.post.dto.request.PostRequest;
import com.urban.backend.shared.dto.response.PageResponse;
import com.urban.backend.features.post.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Value("${FB_TOKEN}")
    private String fbToken;

    @Value("${FB_PAGE}")
    private String fbPage;

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
            publishToFacebook(request);

            return PostResponse.fromPost(savedPost);
        } catch (IOException e) {
            throw new RuntimeException("Error processing post data", e);
        }
    }

    private Post findPostById(UUID postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));
    }

    private void publishToFacebook(PostRequest request) {
        String url = "https://graph.facebook.com/v23.0/" + fbPage + "/photos";

        try {
            ByteArrayResource imageResource = new ByteArrayResource(request.photo().getBytes()) {
                @Override
                public String getFilename() {
                    return request.photo().getOriginalFilename();
                }
            };

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("access_token", fbToken);
            body.add("message", "💪 " + request.title() + "\n\n" + request.content());
            body.add("source", imageResource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(url, requestEntity, String.class);

        } catch (IOException e) {
            throw new RuntimeException("Error processing image data for Facebook post", e);
        }
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
