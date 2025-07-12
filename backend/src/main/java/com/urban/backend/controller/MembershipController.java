package com.urban.backend.controller;

import com.urban.backend.dto.response.MembershipResponse;
import com.urban.backend.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/memberships")
@RequiredArgsConstructor
public class MembershipController {
    private final MembershipService membershipService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<MembershipResponse> getMembershipByUserId(@PathVariable UUID id) {
        MembershipResponse membershipResponse = membershipService.getMembershipByUserId(id);
        return ResponseEntity.ok(membershipResponse);
    }
}
