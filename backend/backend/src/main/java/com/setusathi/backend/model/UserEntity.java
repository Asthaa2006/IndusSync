package com.setusathi.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId; // e.g. APL-2026-0889, OFF-MP-808, ADMIN-ROOT-99

    private String password;
    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    public UserEntity() {}

    public UserEntity(String userId, String password, String name, Role role) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getUserId() { return userId; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public Role getRole() { return role; }
    public void setPassword(String password) { this.password = password; }
}