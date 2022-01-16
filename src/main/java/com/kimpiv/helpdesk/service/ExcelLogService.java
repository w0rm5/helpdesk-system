package com.kimpiv.helpdesk.service;

import java.util.List;

import com.kimpiv.helpdesk.model.ExcelLog;
import com.kimpiv.helpdesk.model.UserInfo;

public interface ExcelLogService {
	void save(ExcelLog log);
	List<ExcelLog> getLogsByExporter(UserInfo exporter);
}
