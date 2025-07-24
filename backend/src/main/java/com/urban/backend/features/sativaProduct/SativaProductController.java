package com.urban.backend.features.sativaProduct;

import com.urban.backend.features.sativaProduct.dto.response.SativaProductResponse;
import com.urban.backend.shared.dto.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sativa-products")
@RequiredArgsConstructor
public class SativaProductController {
    private final SativaProductService service;

    @GetMapping
    public ResponseEntity<PageResponse<SativaProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));

        return ResponseEntity.ok(service.searchProducts(query, pageable));
    }
}
