package com.urban.backend.features.sativaCategory.dto.response;

import java.util.List;

public record CategoriesListResponse(
        List<SativaCategoryResponse> data
) {
}
