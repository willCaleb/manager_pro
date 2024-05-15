package com.project.pro.repository;

import com.project.pro.model.entity.ChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeLogRepository extends JpaRepository<ChangeLog, String> {
}
