package com.kimpiv.helpdesk.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ExcelLog {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="exporter_id")
	private UserInfo exporter;
	
	private String dateTime;
	
	private String query;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserInfo getExporter() {
		return exporter;
	}

	public void setExporter(UserInfo exporter) {
		this.exporter = exporter;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public ExcelLog(UserInfo exporter, String dateTime, String query) {
		this.exporter = exporter;
		this.dateTime = dateTime;
		this.query = query;
	}

	public ExcelLog() {
	}
	
	
	
}
