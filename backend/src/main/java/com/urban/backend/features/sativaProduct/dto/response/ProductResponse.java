package com.urban.backend.features.sativaProduct.dto.response;

import java.util.List;

public record ProductResponse(
        Long id,
        String title,
        Double price,
        String image,
        List<Category> categories
) {
    public record Category(Long id, String name, String img) {}
}
