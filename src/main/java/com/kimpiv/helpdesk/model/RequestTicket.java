package com.kimpiv.helpdesk.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="request_ticket")
public class RequestTicket {
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="requester_id", nullable = false)
	private UserInfo requester;
	
	@ManyToOne
	@JoinColumn(name="helper_id")
	private UserInfo helper;
	
	@ManyToOne
	@JoinColumn(name="category_id", nullable = false)
	private Category category;
	
	@Column(nullable = false)
	private String details;
	
	private String responseFromHelper;

	private boolean drafted;
	
	private int status;
	
	private LocalDate date;
	
	
	public RequestTicket() {}

	public RequestTicket(Long id, UserInfo requester, UserInfo helper, Category category, String details,
			String responseFromHelper, boolean drafted, int status) {
		this.requester = requester;
		this.helper = helper;
		this.category = category;
		this.details = details;
		this.responseFromHelper = responseFromHelper;
		this.drafted = drafted;
		this.status = status;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	public String getResponseFromHelper() {
		return responseFromHelper;
	}

	public void setResponseFromHelper(String responseFromHelper) {
		this.responseFromHelper = responseFromHelper;
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

	public Long getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
}
