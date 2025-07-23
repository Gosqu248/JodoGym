package com.urban.backend.features.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,
        @NotBlank(message = "New password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String newPassword,
        @NotBlank(message = "Confirm new password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String confirmNewPassword
) {
}
