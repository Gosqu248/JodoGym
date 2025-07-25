package com.urban.backend.features.resetCode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResetCodeRepository extends JpaRepository<ResetCode, UUID> {
}
