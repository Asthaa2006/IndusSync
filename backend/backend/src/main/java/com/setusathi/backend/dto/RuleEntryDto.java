package com.setusathi.backend.dto;

import com.setusathi.backend.model.Scope;

public class RuleEntryDto {
    private String name;
    private Scope scope;
    private boolean reused;

    public RuleEntryDto(String name, Scope scope, boolean reused) {
        this.name = name;
        this.scope = scope;
        this.reused = reused;
    }

    public String getName() { return name; }
    public Scope getScope() { return scope; }
    public boolean isReused() { return reused; }
}