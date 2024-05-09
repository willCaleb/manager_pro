package com.project.pro.repository;

import com.project.pro.model.entity.SendEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SendEmailRepository extends JpaRepository<SendEmail, Integer>, JpaSpecificationExecutor<SendEmail>{
}
