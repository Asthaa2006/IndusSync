package com.setusathi.backend.repository;

import com.setusathi.backend.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Optional<Application> findByRef(String ref);
    List<Application> findByApplicantNameIgnoreCase(String applicantName);
}