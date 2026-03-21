package com.spms.claim_service.repo;

import com.spms.claim_service.module.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepo extends JpaRepository<Claim, Long> {
    boolean existsByItemIdAndClaimantUserId(String itemId, Long claimantUserId);
}