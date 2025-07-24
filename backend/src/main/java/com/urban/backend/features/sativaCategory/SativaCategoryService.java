package com.urban.backend.features.sativaCategory;

import com.urban.backend.features.sativaCategory.dto.response.CategoriesListResponse;
import com.urban.backend.features.sativaCategory.dto.response.SativaCategoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SativaCategoryService {
    private final RestTemplate restTemplate;
    private final SativaCategoryRepository repository;

    public List<SativaCategoryResponse> getCategories() {
        return repository.findAll().stream()
                .map(SativaCategoryResponse::fromCategory)
                .toList();
    }


    public void fetchCategories() {
        String url = "https://www.sativalife.eu/api/new/categories";
        ResponseEntity<CategoriesListResponse> response = restTemplate.getForEntity(url, CategoriesListResponse.class);
        List<SativaCategoryResponse> categories = Optional.ofNullable(response.getBody())
                .map(CategoriesListResponse::data)
                .orElse(List.of());

        if (categories.isEmpty()) return;

        for (SativaCategoryResponse dto : categories) {
            Optional<SativaCategory> existingCategory = repository.findById(dto.id());

            if (existingCategory.isEmpty()) {
                SativaCategory sativaCategory = SativaCategory.builder()
                        .id(dto.id())
                        .name(dto.name())
                        .img(dto.img())
                        .build();

                repository.save(sativaCategory);
            }
        }
        log.info("Fetched {} categories from SativaLife API", categories.size());
    }

    public SativaCategory findById(Long id) {
        return repository.findById(id)
                .orElse(null);
    }
}
