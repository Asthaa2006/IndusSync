package com.setusathi.backend.dto;

public class AuthDtos {

    public record LoginRequest(String userId, String password) {}

    public record AuthResponse(String token, String userId, String name, String role) {}
}