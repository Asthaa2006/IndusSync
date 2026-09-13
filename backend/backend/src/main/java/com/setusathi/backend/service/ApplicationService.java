package com.setusathi.backend.service;

import com.setusathi.backend.dto.*;
import com.setusathi.backend.model.*;
import com.setusathi.backend.repository.ApplicationRepository;
import com.setusathi.backend.repository.RequirementRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final RequirementRepository requirementRepository;
    private final RuleEngineService ruleEngineService;

    public ApplicationService(ApplicationRepository applicationRepository,
                              RequirementRepository requirementRepository,
                              RuleEngineService ruleEngineService) {
        this.applicationRepository = applicationRepository;
        this.requirementRepository = requirementRepository;
        this.ruleEngineService = ruleEngineService;
    }

    public Application createApplication(NewApplicationRequest req) {
        Application app = new Application();
        app.setRef(generateRef());
        app.setBusinessName(req.getBusinessName());
        app.setApplicantName(req.getApplicantName() == null ? "Harsh Parmar" : req.getApplicantName());
        app.setIndustry(req.getIndustry());
        app.setScale(req.getScale());
        app.setPremises(req.getPremises());
        app.setStatus(AppStatus.SUBMITTED);
        app.setCreatedAt(LocalDateTime.now());

        List<RuleEntryDto> resolved = ruleEngineService.resolve(req.getIndustry(), req.getScale());
        for (RuleEntryDto entry : resolved) {
            ReqStatus initialStatus = entry.isReused() ? ReqStatus.APPROVED : ReqStatus.PENDING;
            String dept = ruleEngineService.departmentFor(entry.getName());
            RequirementItem item = new RequirementItem(entry.getName(), entry.getScope(), entry.isReused(), dept, initialStatus);
            app.addItem(item);
        }

        return applicationRepository.save(app);
    }

    public Application getByRef(String ref) {
        return applicationRepository.findByRef(ref)
                .orElseThrow(() -> new RuntimeException("No application found for ref " + ref));
    }

    public List<Application> listAll(String applicantName) {
        if (applicantName == null || applicantName.isBlank()) {
            return applicationRepository.findAll();
        }
        return applicationRepository.findByApplicantNameIgnoreCase(applicantName);
    }

    public Application uploadDocument(String ref, Long itemId) {
        Application app = getByRef(ref);
        RequirementItem item = app.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Requirement item not found"));
        if (item.isReused()) {
            throw new RuntimeException("This requirement is reused from profile — no upload needed");
        }
        item.setStatus(ReqStatus.UPLOADED);
        return applicationRepository.save(app);
    }

    public List<QueueItemDto> getQueue() {
        List<RequirementItem> pending = requirementRepository
                .findByReusedFalseAndStatusIn(List.of(ReqStatus.PENDING, ReqStatus.UPLOADED));

        return pending.stream().map(item -> new QueueItemDto(
                item.getId(),
                item.getApplication().getRef(),
                item.getApplication().getBusinessName(),
                item.getApplication().getApplicantName(),
                item.getName(),
                item.getScope(),
                item.getDepartment(),
                item.getStatus()
        )).toList();
    }

    public void approveItem(Long itemId) {
        RequirementItem item = requirementRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Requirement item not found"));
        item.setStatus(ReqStatus.APPROVED);
        requirementRepository.save(item);
        refreshApplicationStatus(item.getApplication());
    }

    public void rejectItem(Long itemId, String reason) {
        RequirementItem item = requirementRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Requirement item not found"));
        item.setStatus(ReqStatus.REJECTED);
        item.setRejectReason(reason);
        requirementRepository.save(item);

        Application app = item.getApplication();
        app.setStatus(AppStatus.REJECTED);
        applicationRepository.save(app);
    }

    private void refreshApplicationStatus(Application app) {
        boolean allDone = app.getItems().stream()
                .allMatch(i -> i.isReused() || i.getStatus() == ReqStatus.APPROVED);
        boolean anyRejected = app.getItems().stream().anyMatch(i -> i.getStatus() == ReqStatus.REJECTED);

        if (anyRejected) {
            app.setStatus(AppStatus.REJECTED);
        } else if (allDone) {
            app.setStatus(AppStatus.APPROVED);
        } else {
            app.setStatus(AppStatus.UNDER_REVIEW);
        }
        applicationRepository.save(app);
    }

    public java.util.Map<String, Object> getStats() {
        List<Application> all = applicationRepository.findAll();
        long approved = all.stream().filter(a -> a.getStatus() == AppStatus.APPROVED).count();
        long rejected = all.stream().filter(a -> a.getStatus() == AppStatus.REJECTED).count();
        long pending = all.size() - approved - rejected;

        return java.util.Map.of(
                "totalApplications", all.size(),
                "approved", approved,
                "rejected", rejected,
                "pendingOrUnderReview", pending
        );
    }

    private String generateRef() {
        int num = 1000 + new Random().nextInt(9000);
        return "REQ-2026-" + num;
    }
}