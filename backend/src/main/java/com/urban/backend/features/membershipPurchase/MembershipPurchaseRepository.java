package com.urban.backend.features.membershipPurchase;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MembershipPurchaseRepository extends JpaRepository<MembershipPurchase, UUID> {
    Page<MembershipPurchase> findAllByMembershipId(UUID membershipId, Pageable pageable);
}
