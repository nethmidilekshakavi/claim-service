package com.spms.claim_service.module;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemId;           // MongoDB item ID (String)
    private Long claimantUserId;     // MySQL user ID (Long)
    private String claimantEmail;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String status = "PENDING"; // PENDING / APPROVED / REJECTED

    private long createdAt;
    private long updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = System.currentTimeMillis();
    }
}