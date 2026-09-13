package com.setusathi.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class RequirementItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Scope scope;

    private boolean reused;

    @Enumerated(EnumType.STRING)
    private ReqStatus status;

    private String department;

    private String rejectReason;

    @ManyToOne
    @JoinColumn(name = "application_id")
    @JsonBackReference
    private Application application;

    public RequirementItem() {}

    public RequirementItem(String name, Scope scope, boolean reused, String department, ReqStatus status) {
        this.name = name;
        this.scope = scope;
        this.reused = reused;
        this.department = department;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Scope getScope() { return scope; }
    public void setScope(Scope scope) { this.scope = scope; }
    public boolean isReused() { return reused; }
    public void setReused(boolean reused) { this.reused = reused; }
    public ReqStatus getStatus() { return status; }
    public void setStatus(ReqStatus status) { this.status = status; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getRejectReason() { return rejectReason; }
    public void setRejectReason(String rejectReason) { this.rejectReason = rejectReason; }
    public Application getApplication() { return application; }
    public void setApplication(Application application) { this.application = application; }
}
