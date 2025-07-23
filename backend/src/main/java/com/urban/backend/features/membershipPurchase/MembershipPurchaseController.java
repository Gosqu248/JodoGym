package com.urban.backend.features.membershipPurchase;

import com.urban.backend.features.membershipPurchase.dto.request.MembershipPurchaseRequest;
import com.urban.backend.features.membershipPurchase.dto.response.MembershipPurchaseResponse;
import com.urban.backend.shared.dto.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class MembershipPurchaseController {
    private final MembershipPurchaseService membershipPurchaseService;

    @GetMapping("/{id}")
    public ResponseEntity<PageResponse<MembershipPurchaseResponse>> searchPurchase(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));

        PageResponse<MembershipPurchaseResponse> response =
                membershipPurchaseService.getAllByMembershipId(id, pageable);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<MembershipPurchaseResponse> createMembershipPurchase(
            @PathVariable UUID id,
            @RequestBody MembershipPurchaseRequest request) {

        MembershipPurchaseResponse response =
                membershipPurchaseService.createMembershipPurchase(id, request);

        return ResponseEntity.ok(response);
    }
}
