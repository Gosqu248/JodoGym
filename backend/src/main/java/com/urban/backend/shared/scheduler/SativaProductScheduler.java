package com.urban.backend.shared.scheduler;

import com.urban.backend.features.sativaCategory.SativaCategoryService;
import com.urban.backend.features.sativaProduct.SativaProductService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SativaProductScheduler {
    private final SativaProductService productService;
    private final SativaCategoryService categoryService;

    @PostConstruct
    public void initProducts() {
        categoryService.fetchCategories();
        productService.fetchProducts();
    }

    @Scheduled(cron = "0 0 1 ? * MON") // co poniedziałek o 01:00
    public void fetchWeekly() {
        productService.fetchProducts();
    }
}
