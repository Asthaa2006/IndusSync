package com.setusathi.backend.controller;

import com.setusathi.backend.dto.QueueItemDto;
import com.setusathi.backend.dto.RejectRequest;
import com.setusathi.backend.service.ApplicationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/queue")
public class QueueController {

    private final ApplicationService applicationService;

    public QueueController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public List<QueueItemDto> getQueue() {
        return applicationService.getQueue();
    }

    @PatchMapping("/{itemId}/approve")
    public Map<String, String> approve(@PathVariable Long itemId) {
        applicationService.approveItem(itemId);
        return Map.of("status", "approved");
    }

    @PatchMapping("/{itemId}/reject")
    public Map<String, String> reject(@PathVariable Long itemId, @RequestBody RejectRequest body) {
        applicationService.rejectItem(itemId, body.getReason());
        return Map.of("status", "rejected");
    }
}