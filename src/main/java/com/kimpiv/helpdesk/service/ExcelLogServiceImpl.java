package com.kimpiv.helpdesk.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kimpiv.helpdesk.model.ExcelLog;
import com.kimpiv.helpdesk.model.UserInfo;
import com.kimpiv.helpdesk.repository.ExcelLogRepository;

@Service
public class ExcelLogServiceImpl implements ExcelLogService {

	private final ExcelLogRepository excelLogRepository;

	public ExcelLogServiceImpl(ExcelLogRepository excelLogRepository) {
		super();
		this.excelLogRepository = excelLogRepository;
	}

	@Override
	public void save(ExcelLog log) {
		excelLogRepository.save(log);
	}

	@Override
	public List<ExcelLog> getLogsByExporter(UserInfo exporter) {
		// TODO Auto-generated method stub
		return excelLogRepository.findByExporter(exporter);
	}

}
