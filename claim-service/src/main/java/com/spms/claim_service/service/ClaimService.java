package com.spms.claim_service.service;

import com.spms.claim_service.dto.ClaimDto;
import com.spms.claim_service.entity.Claim;

import java.util.List;

public interface ClaimService {

    Claim createClaim(ClaimDto dto);

    List<Claim> getAllClaims();

    Claim getClaimById(Long id);

    List<Claim> getClaimsByItem(String itemId);

    List<Claim> getClaimsByUser(Long userId);

    List<Claim> getClaimsByStatus(String status);

    Claim updateClaimStatus(Long id, String status);

    void deleteClaim(Long id);
}