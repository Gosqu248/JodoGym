package com.urban.backend.controller;

import com.urban.backend.dto.request.MembershipPurchaseRequest;
import com.urban.backend.dto.response.MembershipPurchaseResponse;
import com.urban.backend.dto.response.PageResponse;
import com.urban.backend.service.MembershipPurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/purchase")
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
