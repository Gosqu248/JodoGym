package com.urban.backend.dto.request;

import com.urban.backend.enums.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record PostRequest(
        @NotBlank(message = "Title name is required")
        @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
        String title,
        @NotBlank(message = "Content is required")
        @Size(min = 2, max = 2000, message = "Title must be between 2 and 2000 characters")
        String content,
        @NotNull(message = "Post type is required")
        PostType postType,
        @NotNull(message = "Photo is required")
        MultipartFile photo
) {
}
