package com.urban.backend.features.sativaProduct;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SativaProductRepository extends JpaRepository<SativaProduct, Long> {
    @Query("""
        SELECT p FROM SativaProduct p
        JOIN p.categories c
        WHERE c.id = :categoryId
    """)
    Page<SativaProduct> findBySearchQuery(@Param("categoryId") Long categoryId, Pageable pageable);
}
