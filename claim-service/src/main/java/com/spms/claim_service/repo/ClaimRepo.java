package com.spms.claim_service.repo;

import com.spms.claim_service.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRepo extends JpaRepository<Claim, Long> {

    List<Claim> findByItemId(String itemId);

    List<Claim> findByClaimantUserId(Long userId);

    List<Claim> findByStatus(Claim.ClaimStatus status);

    boolean existsByItemIdAndClaimantUserId(String itemId, Long userId);
}