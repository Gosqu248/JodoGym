package com.urban.backend.features.sativaCategory;

import com.urban.backend.features.sativaCategory.dto.response.SativaCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sativa-categories")
@RequiredArgsConstructor
public class SativaCategoryController {
    private final SativaCategoryService service;

    @GetMapping
    public ResponseEntity<List<SativaCategoryResponse>> getProductCategories() {
        List<SativaCategoryResponse> categories = service.getCategories();
        return ResponseEntity.ok(categories);
    }
}
