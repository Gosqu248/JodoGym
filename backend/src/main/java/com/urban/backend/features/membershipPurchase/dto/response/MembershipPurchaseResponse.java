package com.urban.backend.features.membershipPurchase.dto.response;

import com.urban.backend.features.membershipPurchase.MembershipPurchase;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record MembershipPurchaseResponse(
        UUID id,
        Instant purchaseDate,
        int durationMonths,
        int durationWeeks,
        BigDecimal price
) {
    public static MembershipPurchaseResponse fromPurchase(MembershipPurchase membershipPurchase) {
        return new MembershipPurchaseResponse(
                membershipPurchase.getId(),
                membershipPurchase.getPurchaseDate(),
                membershipPurchase.getDurationMonths(),
                membershipPurchase.getDurationWeeks(),
                membershipPurchase.getPrice()
        );
    }

}
