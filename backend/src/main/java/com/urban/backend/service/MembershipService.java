package com.urban.backend.service;

import com.urban.backend.dto.response.MembershipResponse;
import com.urban.backend.model.Membership;
import com.urban.backend.model.User;
import com.urban.backend.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MembershipService {
    private final MembershipRepository membershipRepository;

    public MembershipResponse getMembershipByUserId(UUID userId) {
        return membershipRepository.findByUserId(userId)
                .map(MembershipResponse::fromMembership)
                .orElse(null);
    }

    public void createMembershipForUser(User user) {
        if (membershipRepository.existsByUserId(user.getId())) {
            throw new IllegalArgumentException("User already has a membership.");
        }

        Membership membership = Membership.builder()
                .user(user)
                .isFrozen(false)
                .expiryDate(null)
                .build();
        membershipRepository.save(membership);
    }

    public void updateMembership(Membership membership, int durationMoths, int durationWeeks) {
        Instant now = Instant.now();
        Instant start = (membership.getExpiryDate() != null && membership.getExpiryDate().isAfter(now))
                ? membership.getExpiryDate()
                : now;

        ZonedDateTime startDateTime = ZonedDateTime.ofInstant(start, ZoneId.systemDefault());
        ZonedDateTime newExpiryDateTime = startDateTime
                .plusMonths(durationMoths)
                .plusWeeks(durationWeeks);

        Instant newExpiryDate = newExpiryDateTime.toInstant();
        membership.setExpiryDate(newExpiryDate);

        membershipRepository.save(membership);
    }

    public Membership getMembershipById(UUID id) {
        return membershipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Membership not found for user ID: " + id));
    }
}
