package com.setusathi.backend.repository;

import com.setusathi.backend.model.ReqStatus;
import com.setusathi.backend.model.RequirementItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RequirementRepository extends JpaRepository<RequirementItem, Long> {
    List<RequirementItem> findByReusedFalseAndStatusIn(List<ReqStatus> statuses);
}