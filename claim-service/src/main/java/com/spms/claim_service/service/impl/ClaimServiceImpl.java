package com.spms.claim_service.service.impl;

import com.spms.claim_service.client.ItemClient;
import com.spms.claim_service.client.UserClient;
import com.spms.claim_service.dto.ClaimDto;
import com.spms.claim_service.module.Claim;
import com.spms.claim_service.repo.ClaimRepo;
import com.spms.claim_service.service.ClaimService;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepo claimRepository;
    private final UserClient userServiceClient;
    private final ItemClient itemServiceClient;

    public ClaimServiceImpl(ClaimRepo claimRepository, UserClient userServiceClient, ItemClient itemServiceClient) {
        this.claimRepository = claimRepository;
        this.userServiceClient = userServiceClient;
        this.itemServiceClient = itemServiceClient;
    }

    @Override
    public Claim createClaim(ClaimDto dto) {
        try {
            userServiceClient.getUserById(dto.getClaimantUserId());
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("User not found with id: " + dto.getClaimantUserId());
        }

        Object itemData;
        try {
            itemData = itemServiceClient.getItemById(dto.getItemId());
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Item not found with id: " + dto.getItemId());
        }

        String itemStr = itemData.toString();
        if (itemStr.contains("\"status\":\"CLAIMED\"") || itemStr.contains("status=CLAIMED")) {
            throw new RuntimeException("Item is already claimed.");
        }
        if (itemStr.contains("\"status\":\"CLOSED\"") || itemStr.contains("status=CLOSED")) {
            throw new RuntimeException("Item is closed and cannot be claimed.");
        }

        boolean alreadyClaimed = claimRepository
                .existsByItemIdAndClaimantUserId(dto.getItemId(), dto.getClaimantUserId());
        if (alreadyClaimed) {
            throw new RuntimeException("You have already submitted a claim for this item.");
        }

        Claim claim = new Claim();
        claim.setItemId(dto.getItemId());
        claim.setClaimantUserId(dto.getClaimantUserId());
        claim.setClaimantEmail(dto.getClaimantEmail());
        claim.setDescription(dto.getDescription());
        claim.setStatus("PENDING");

        return claimRepository.save(claim);
    }

    @Override
    public List<Claim> getAllClaims() {
        return claimRepository.findAll();
    }

    @Override
    public Claim getClaimById(Long id) {
        return claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found with id: " + id));
    }

    @Override
    public Claim updateClaimStatus(Long id, String status) {
        Claim claim = getClaimById(id);
        claim.setStatus(status.toUpperCase());

        if ("APPROVED".equalsIgnoreCase(status)) {
            try {
                itemServiceClient.updateItemStatus(claim.getItemId(), "CLAIMED");
            } catch (Exception e) {
                System.err.println("Warning: Could not update item status - " + e.getMessage());
            }
        }

        return claimRepository.save(claim);
    }

    @Override
    public void deleteClaim(Long id) {
        if (!claimRepository.existsById(id)) {
            throw new RuntimeException("Claim not found with id: " + id);
        }
        claimRepository.deleteById(id);
    }
}