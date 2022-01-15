package com.kimpiv.helpdesk.service;

import java.util.List;

import com.kimpiv.helpdesk.model.Category;
import com.kimpiv.helpdesk.model.RequestTicket;
import com.kimpiv.helpdesk.model.UserInfo;
import com.kimpiv.helpdesk.service.web.dto.RequestTicketDto;

public interface RequestTicketService {
	RequestTicket save(RequestTicket ticket);
	RequestTicket findById(Long id);
	void deleteById(Long id);
	List<RequestTicket> findByRequester(UserInfo requester);
	List<RequestTicket> findByHelper(UserInfo helper);
	List<RequestTicket> findByCategory(Category category);
	List<RequestTicket> findByDrafted(boolean isDrafted);
	List<RequestTicket> findByStatus(int status);
	RequestTicketDto convertToDto(RequestTicket ticket);
	List<RequestTicketDto> convertToDtoList(List<RequestTicket> tickets);
}
