package com.setusathi.backend.dto;

public class NewApplicationRequest {
    private String businessName;
    private String applicantName;
    private String industry;
    private String scale;
    private String premises;

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }
    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }
    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }
    public String getScale() { return scale; }
    public void setScale(String scale) { this.scale = scale; }
    public String getPremises() { return premises; }
    public void setPremises(String premises) { this.premises = premises; }
}