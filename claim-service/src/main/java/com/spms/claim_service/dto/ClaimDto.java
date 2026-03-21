package com.spms.claim_service.dto;

import lombok.Data;

@Data
public class ClaimDto {
    private String itemId;
    private Long claimantUserId;
    private String claimantEmail;
    private String description;
}