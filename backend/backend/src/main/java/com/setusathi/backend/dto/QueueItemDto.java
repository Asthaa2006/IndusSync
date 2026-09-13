package com.setusathi.backend.dto;

import com.setusathi.backend.model.ReqStatus;
import com.setusathi.backend.model.Scope;

public class QueueItemDto {
    private Long itemId;
    private String applicationRef;
    private String businessName;
    private String applicantName;
    private String requirementName;
    private Scope scope;
    private String department;
    private ReqStatus status;

    public QueueItemDto(Long itemId, String applicationRef, String businessName, String applicantName,
                        String requirementName, Scope scope, String department, ReqStatus status) {
        this.itemId = itemId;
        this.applicationRef = applicationRef;
        this.businessName = businessName;
        this.applicantName = applicantName;
        this.requirementName = requirementName;
        this.scope = scope;
        this.department = department;
        this.status = status;
    }

    public Long getItemId() { return itemId; }
    public String getApplicationRef() { return applicationRef; }
    public String getBusinessName() { return businessName; }
    public String getApplicantName() { return applicantName; }
    public String getRequirementName() { return requirementName; }
    public Scope getScope() { return scope; }
    public String getDepartment() { return department; }
    public ReqStatus getStatus() { return status; }
}