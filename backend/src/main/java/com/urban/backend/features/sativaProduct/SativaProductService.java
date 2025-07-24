package com.urban.backend.features.sativaProduct;

import com.urban.backend.features.sativaCategory.SativaCategory;
import com.urban.backend.features.sativaCategory.SativaCategoryService;
import com.urban.backend.features.sativaProduct.dto.response.*;
import com.urban.backend.shared.dto.response.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SativaProductService {
    private final RestTemplate restTemplate;
    private final SativaProductRepository repository;
    private final SativaCategoryService sativaCategoryService;


    public PageResponse<SativaProductResponse> searchProducts(String query, Pageable pageable) {
        Page<SativaProduct> productsPage;

        if (query.trim().isEmpty()) {
            productsPage = repository.findAll(pageable);
        } else {
            productsPage = repository.findBySearchQuery(query, pageable);
        }

        Page<SativaProductResponse> mappedPage = productsPage.map(SativaProductResponse::fromSativaProduct);
        return PageResponse.from(mappedPage);
    }

    public void fetchProducts() {
        String url = "https://www.sativalife.eu/api/new/products";
        ResponseEntity<ProductListResponse> response = restTemplate.getForEntity(url, ProductListResponse.class);
        List<ProductResponse> products = Optional.ofNullable(response.getBody())
                .map(ProductListResponse::data)
                .orElse(List.of());

        if (products.isEmpty()) return;

        for (ProductResponse dto : products) {
            Optional<SativaProduct> existingProduct = repository.findById(dto.id());

            boolean shouldSave = false;

            if (existingProduct.isEmpty()) {
                shouldSave = true;
            } else {
                SativaProduct existing = existingProduct.get();
                if (!Objects.equals(existing.getPrice(), dto.price())) {
                    shouldSave = true;
                }
            }

            if (shouldSave) {
                Set<SativaCategory> categories = dto.categories().stream()
                        .map(category -> sativaCategoryService.findById(category.id()))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());

                SativaProduct sativaProduct = SativaProduct.builder()
                        .id(dto.id())
                        .title(dto.title())
                        .price(dto.price())
                        .image(dto.image())
                        .productUrl("https://www.sativalife.eu/app/product/" + dto.id())
                        .categories(categories)
                        .build();

                repository.save(sativaProduct);
            }
        }
        log.info("Updated Sativa products from API");
    }
}
