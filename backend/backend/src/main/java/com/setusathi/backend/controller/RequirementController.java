package com.setusathi.backend.controller;

import com.setusathi.backend.dto.RuleEntryDto;
import com.setusathi.backend.service.RuleEngineService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/requirements")
public class RequirementController {

    private final RuleEngineService ruleEngineService;

    public RequirementController(RuleEngineService ruleEngineService) {
        this.ruleEngineService = ruleEngineService;
    }

    @GetMapping
    public List<RuleEntryDto> resolve(@RequestParam String industry, @RequestParam String scale) {
        return ruleEngineService.resolve(industry, scale);
    }
}