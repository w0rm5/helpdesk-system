package com.kimpiv.helpdesk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kimpiv.helpdesk.model.ExcelLog;
import com.kimpiv.helpdesk.model.UserInfo;

public interface ExcelLogRepository extends JpaRepository<ExcelLog, Long>{
	List<ExcelLog> findByExporter(UserInfo userInfo);
}
