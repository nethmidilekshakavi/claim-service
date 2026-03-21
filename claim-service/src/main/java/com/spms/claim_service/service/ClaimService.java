package com.spms.claim_service.service;

import com.spms.claim_service.dto.ClaimDto;
import com.spms.claim_service.module.Claim;

import java.util.List;

public interface ClaimService {
    Claim createClaim(ClaimDto dto);
    List<Claim> getAllClaims();
    Claim getClaimById(Long id);
    Claim updateClaimStatus(Long id, String status);
    void deleteClaim(Long id);
}