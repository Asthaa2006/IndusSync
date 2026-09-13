package com.setusathi.backend.controller;

import com.setusathi.backend.service.ApplicationService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ApplicationService applicationService;

    public AdminController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/stats")
    public Map<String, Object> stats() {
        return applicationService.getStats();
    }
}