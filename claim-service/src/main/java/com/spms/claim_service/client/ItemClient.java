package com.spms.claim_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ITEM-SERVICE")
public interface ItemClient {

    @GetMapping("/api/items/{id}")
    Object getItemById(@PathVariable("id") String id);

    @PatchMapping("/api/items/{id}/status")
    Object updateItemStatus(@PathVariable("id") String id,
                            @RequestParam("status") String status);
}