package com.kimpiv.helpdesk.service.web.dto;

import java.time.LocalDate;

import com.kimpiv.helpdesk.model.Category;
import com.kimpiv.helpdesk.model.UserInfo;

public class RequestTicketDto {
	
	private Long id;

	private UserInfo requester;
	
	private UserInfo helper;
	
	private Category mainCategory;
	
	private Category subCategory;
	
	private String details;
	
	private String responseFromHelper;
	
	private boolean drafted;
	
	private int status;
	
	private LocalDate date;
	

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public RequestTicketDto() {}

	public RequestTicketDto(UserInfo requester) {
		this.requester = requester;
	}
	
	public RequestTicketDto(Long id, UserInfo requester, UserInfo helper, Category mainCategory, Category subCategory,
			String details, String responseFromHelper, boolean drafted, int status, LocalDate date) {
		this.id = id;
		this.requester = requester;
		this.helper = helper;
		this.mainCategory = mainCategory;
		this.subCategory = subCategory;
		this.details = details;
		this.responseFromHelper = responseFromHelper;
		this.drafted = drafted;
		this.status = status;
		this.date = date;
	}
	
	public Long getId() {
		return id;
	}

	public UserInfo getRequester() {
		return requester;
	}

	public void setRequester(UserInfo requester) {
		this.requester = requester;
	}

	public UserInfo getHelper() {
		return helper;
	}

	public void setHelper(UserInfo helper) {
		this.helper = helper;
	}

	public Category getMainCategory() {
		return mainCategory;
	}

	public void setMainCategory(Category mainCategory) {
		this.mainCategory = mainCategory;
	}

	public Category getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(Category subCategory) {
		this.subCategory = subCategory;
	}

	public String getDetails() {
		return details;
	}
	
	public String getResponseFromHelper() {
		return responseFromHelper;
	}

	public void setResponseFromHelper(String responseFromHelper) {
		this.responseFromHelper = responseFromHelper;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public boolean isDrafted() {
		return drafted;
	}

	public void setDrafted(boolean drafted) {
		this.drafted = drafted;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
