package com.urban.backend.dto.response;

public record ResultResponse(
        boolean success,
        String message
) {
}
