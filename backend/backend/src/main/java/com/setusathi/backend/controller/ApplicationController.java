package com.setusathi.backend.controller;

import com.setusathi.backend.dto.NewApplicationRequest;
import com.setusathi.backend.model.Application;
import com.setusathi.backend.service.ApplicationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public Application create(@RequestBody NewApplicationRequest req) {
        return applicationService.createApplication(req);
    }

    @GetMapping("/{ref}")
    public Application getOne(@PathVariable String ref) {
        return applicationService.getByRef(ref);
    }

    @GetMapping
    public List<Application> listAll(@RequestParam(required = false) String applicant) {
        return applicationService.listAll(applicant);
    }

    @PatchMapping("/{ref}/requirements/{itemId}/upload")
    public Application upload(@PathVariable String ref, @PathVariable Long itemId) {
        return applicationService.uploadDocument(ref, itemId);
    }
}