package com.spms.claim_service.dto;

public class ClaimDto {
    private String itemId;
    private Long claimantUserId;
    private String claimantEmail;
    private String description;

    public String getItemId() { return itemId; }
    public void setItemId(String itemId) { this.itemId = itemId; }

    public Long getClaimantUserId() { return claimantUserId; }
    public void setClaimantUserId(Long claimantUserId) { this.claimantUserId = claimantUserId; }

    public String getClaimantEmail() { return claimantEmail; }
    public void setClaimantEmail(String claimantEmail) { this.claimantEmail = claimantEmail; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}