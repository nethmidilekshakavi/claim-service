package com.spms.claim_service.controller;

import com.spms.claim_service.dto.ClaimDto;
import com.spms.claim_service.module.Claim;
import com.spms.claim_service.service.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/claims")
@CrossOrigin(origins = "*")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    // ─── CREATE ──────────────────────────────────────────────────────────────

    /**
     * POST /api/v1/claims
     * Body: { itemId, claimantUserId, claimantEmail, description }
     */
    @PostMapping
    public ResponseEntity<?> createClaim(@RequestBody ClaimDto dto) {
        try {
            Claim claim = claimService.createClaim(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(claim);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // ─── READ ─────────────────────────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<Claim>> getAllClaims() {
        return ResponseEntity.ok(claimService.getAllClaims());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getClaimById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(claimService.getClaimById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<Claim>> getClaimsByItem(@PathVariable String itemId) {
        return ResponseEntity.ok(claimService.getClaimsByItem(itemId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Claim>> getClaimsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(claimService.getClaimsByUser(userId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getClaimsByStatus(@PathVariable String status) {
        try {
            return ResponseEntity.ok(claimService.getClaimsByStatus(status));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid status. Use: PENDING, APPROVED, REJECTED");
        }
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────

    /**
     * PATCH /api/v1/claims/{id}/status?status=APPROVED
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        try {
            return ResponseEntity.ok(claimService.updateClaimStatus(id, status));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClaim(@PathVariable Long id) {
        try {
            claimService.deleteClaim(id);
            return ResponseEntity.ok("Claim deleted successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ─── HEALTH ───────────────────────────────────────────────────────────────

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("claim-service is running ✓");
    }
}