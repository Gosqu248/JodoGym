package com.urban.backend.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MembershipPurchaseRequest(
        @Min(1)
        int durationMonths,

        @Min(0)
        int durationWeeks,

        @NotNull
        @Min(0)
        BigDecimal price
) {
}
