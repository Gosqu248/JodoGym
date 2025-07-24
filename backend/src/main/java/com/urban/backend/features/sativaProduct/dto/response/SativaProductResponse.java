package com.urban.backend.features.sativaProduct.dto.response;

import com.urban.backend.features.sativaCategory.SativaCategory;
import com.urban.backend.features.sativaProduct.SativaProduct;

import java.util.List;

public record SativaProductResponse(
        Long id,
        String title,
        Double price,
        String image,
        String productUrl,
        List<String> categories
) {
    public static SativaProductResponse fromSativaProduct(SativaProduct sativaProduct) {
        return new SativaProductResponse(
                sativaProduct.getId(),
                sativaProduct.getTitle(),
                sativaProduct.getPrice(),
                sativaProduct.getImage(),
                sativaProduct.getProductUrl(),
                sativaProduct.getCategories().stream()
                        .map(SativaCategory::getName)
                        .toList()
        );
    }
}
