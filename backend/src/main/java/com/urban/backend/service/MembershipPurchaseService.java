package com.urban.backend.service;

import com.urban.backend.dto.request.MembershipPurchaseRequest;
import com.urban.backend.dto.response.MembershipPurchaseResponse;
import com.urban.backend.dto.response.PageResponse;
import com.urban.backend.model.Membership;
import com.urban.backend.model.MembershipPurchase;
import com.urban.backend.repository.MembershipPurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MembershipPurchaseService {
    private final MembershipPurchaseRepository repository;
    private final MembershipService membershipService;

    public PageResponse<MembershipPurchaseResponse> getAllByMembershipId(UUID membershipId, Pageable pageable) {
        Page<MembershipPurchase> purchasePage = repository.findAllByMembershipId(membershipId, pageable);
        Page<MembershipPurchaseResponse> mappedPage = purchasePage.map(MembershipPurchaseResponse::fromPurchase);

        return PageResponse.from(mappedPage);
    }

    public MembershipPurchaseResponse createMembershipPurchase(UUID membershipId, MembershipPurchaseRequest request) {
        Membership membership = membershipService.getMembershipById(membershipId);

        MembershipPurchase membershipPurchase = MembershipPurchase.builder()
                .membership(membership)
                .durationMonths(request.durationMonths())
                .durationWeeks(request.durationWeeks())
                .price(request.price())
                .purchaseDate(Instant.now())
                .build();

        membershipService.updateMembership(membership, request.durationMonths(), request.durationWeeks());
        MembershipPurchase savedPurchase = repository.save(membershipPurchase);

        return MembershipPurchaseResponse.fromPurchase(savedPurchase);
    }
}
