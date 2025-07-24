package com.urban.backend.features.sativaCategory.dto.response;

import com.urban.backend.features.sativaCategory.SativaCategory;

public record SativaCategoryResponse(
        Long id,
        String name,
        String img
) {
    public static SativaCategoryResponse fromCategory(SativaCategory category) {
        return new SativaCategoryResponse(
                category.getId(),
                category.getName(),
                category.getImg()
        );
    }
}
