package com.setusathi.backend.service;

import com.setusathi.backend.dto.RuleEntryDto;
import com.setusathi.backend.model.Scope;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RuleEngineService {

    private final Map<String, Map<String, List<RuleEntryDto>>> rules = new HashMap<>();

    public RuleEngineService() {
        Map<String, List<RuleEntryDto>> food = new HashMap<>();
        food.put("micro", List.of(
                new RuleEntryDto("FSSAI basic registration", Scope.PERSON, true),
                new RuleEntryDto("Health NOC", Scope.LOCATION, false),
                new RuleEntryDto("Shop establishment", Scope.INSTANCE, false)
        ));
        food.put("small", List.of(
                new RuleEntryDto("FSSAI state license", Scope.PERSON, true),
                new RuleEntryDto("Fire safety NOC", Scope.LOCATION, false),
                new RuleEntryDto("Trade license", Scope.LOCATION, false),
                new RuleEntryDto("GUMASTA license", Scope.INSTANCE, false)
        ));

        Map<String, List<RuleEntryDto>> pharmacy = new HashMap<>();
        pharmacy.put("micro", List.of(
                new RuleEntryDto("Pharmacist certification", Scope.PERSON, true),
                new RuleEntryDto("Drug retail license", Scope.INSTANCE, false),
                new RuleEntryDto("Rent agreement", Scope.LOCATION, false)
        ));
        pharmacy.put("small", List.of(
                new RuleEntryDto("Pharmacist certification", Scope.PERSON, true),
                new RuleEntryDto("Drug wholesale license", Scope.INSTANCE, false),
                new RuleEntryDto("Fire safety NOC", Scope.LOCATION, false),
                new RuleEntryDto("Pollution clearance", Scope.LOCATION, false)
        ));

        Map<String, List<RuleEntryDto>> manufacturing = new HashMap<>();
        manufacturing.put("micro", List.of(
                new RuleEntryDto("FSSAI manufacturing license", Scope.PERSON, true),
                new RuleEntryDto("Pollution board consent", Scope.LOCATION, false),
                new RuleEntryDto("Factory license", Scope.INSTANCE, false)
        ));
        manufacturing.put("small", List.of(
                new RuleEntryDto("Central food license", Scope.PERSON, true),
                new RuleEntryDto("High hazard fire clearance", Scope.LOCATION, false),
                new RuleEntryDto("Pollution CTO", Scope.LOCATION, false),
                new RuleEntryDto("Factory registration ID", Scope.INSTANCE, false)
        ));

        rules.put("food", food);
        rules.put("pharmacy", pharmacy);
        rules.put("manufacturing", manufacturing);
    }

    public List<RuleEntryDto> resolve(String industry, String scale) {
        Map<String, List<RuleEntryDto>> byScale = rules.getOrDefault(industry, rules.get("food"));
        return byScale.getOrDefault(scale, byScale.get("micro"));
    }

    public String departmentFor(String requirementName) {
        String n = requirementName.toLowerCase();
        if (n.contains("fire")) return "Fire Department";
        if (n.contains("fssai") || n.contains("food") || n.contains("health")) return "Food Safety & Public Health Dept";
        if (n.contains("pollution")) return "Pollution Control Board";
        if (n.contains("drug") || n.contains("pharmacist")) return "State Drug Control Department";
        if (n.contains("trade") || n.contains("gumasta") || n.contains("shop") || n.contains("rent")) return "Municipal Corporation";
        if (n.contains("factory")) return "Factory Inspectorate";
        return "General Administration";
    }
}