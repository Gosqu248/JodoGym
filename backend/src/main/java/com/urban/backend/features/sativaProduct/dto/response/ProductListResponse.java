package com.urban.backend.features.sativaProduct.dto.response;

import java.util.List;

public record ProductListResponse(
        List<ProductResponse> data
) {
}
