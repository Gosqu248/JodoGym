package com.urban.backend.dto.response;

import com.urban.backend.model.Membership;

import java.time.Instant;
import java.util.UUID;

public record MembershipResponse(
        UUID id,
        Instant expiryDate,
        Boolean isFrozen,
        Boolean isActive
) {
    public static MembershipResponse fromMembership(Membership membership) {
        Boolean isActive = membership.getExpiryDate() != null && membership.getExpiryDate().isAfter(Instant.now());

        return new MembershipResponse(
                membership.getId(),
                membership.getExpiryDate(),
                membership.getIsFrozen(),
                isActive
        );
    }
}
