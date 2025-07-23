package com.urban.backend.shared.dto.response;

public record ResultResponse(
        boolean success,
        String message
) {
}
