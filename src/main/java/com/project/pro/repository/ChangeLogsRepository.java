package com.project.pro.repository;

import com.project.pro.model.entity.ChangeLogs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeLogsRepository extends JpaRepository<ChangeLogs, String> {
}
