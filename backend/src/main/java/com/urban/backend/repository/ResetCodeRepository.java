package com.urban.backend.repository;

import com.urban.backend.model.ResetCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResetCodeRepository extends JpaRepository<ResetCode, UUID> {
}
