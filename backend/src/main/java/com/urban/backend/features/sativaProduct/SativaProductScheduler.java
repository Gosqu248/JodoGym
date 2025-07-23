package com.urban.backend.features.sativaProduct;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SativaProductScheduler {
    private final SativaProductService service;

    @PostConstruct
    public void initProducts() {
        service.updateProducts();
    }

    @Scheduled(cron = "0 0 1 ? * MON") // co poniedziałek o 01:00
    public void fetchWeekly() {
        service.updateProducts();
    }
}
