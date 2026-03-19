package com.spms.claim_service.service.impl;

import com.spms.claim_service.dto.ClaimDto;

import com.spms.claim_service.module.Claim;
import com.spms.claim_service.repo.ClaimRepo;
import com.spms.claim_service.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimServiceImpl implements ClaimService {

    @Autowired
    private ClaimRepo claimRepo;

    @Override
    public Claim createClaim(ClaimDto dto) {
        // Prevent duplicate claim from same user on same item
        if (claimRepo.existsByItemIdAndClaimantUserId(dto.getItemId(), dto.getClaimantUserId())) {
            throw new RuntimeException("You have already submitted a claim for this item.");
        }

        Claim claim = new Claim();
        claim.setItemId(dto.getItemId());
        claim.setClaimantUserId(dto.getClaimantUserId());
        claim.setClaimantEmail(dto.getClaimantEmail());
        claim.setDescription(dto.getDescription());
        claim.setStatus(Claim.ClaimStatus.PENDING);

        return claimRepo.save(claim);
    }

    @Override
    public List<Claim> getAllClaims() {
        return claimRepo.findAll();
    }

    @Override
    public Claim getClaimById(Long id) {
        return claimRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found with id: " + id));
    }

    @Override
    public List<Claim> getClaimsByItem(String itemId) {
        return claimRepo.findByItemId(itemId);
    }

    @Override
    public List<Claim> getClaimsByUser(Long userId) {
        return claimRepo.findByClaimantUserId(userId);
    }

    @Override
    public List<Claim> getClaimsByStatus(String status) {
        Claim.ClaimStatus claimStatus = Claim.ClaimStatus.valueOf(status.toUpperCase());
        return claimRepo.findByStatus(claimStatus);
    }

    @Override
    public Claim updateClaimStatus(Long id, String status) {
        Claim claim = getClaimById(id);
        claim.setStatus(Claim.ClaimStatus.valueOf(status.toUpperCase()));
        return claimRepo.save(claim);
    }

    @Override
    public void deleteClaim(Long id) {
        if (!claimRepo.existsById(id)) {
            throw new RuntimeException("Claim not found with id: " + id);
        }
        claimRepo.deleteById(id);
    }
}