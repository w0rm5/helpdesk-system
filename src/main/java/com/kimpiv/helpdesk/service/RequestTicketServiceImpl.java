package com.kimpiv.helpdesk.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kimpiv.helpdesk.model.Category;
import com.kimpiv.helpdesk.model.RequestTicket;
import com.kimpiv.helpdesk.model.Status;
import com.kimpiv.helpdesk.model.UserInfo;
import com.kimpiv.helpdesk.repository.RequestTicketRepository;
import com.kimpiv.helpdesk.service.web.dto.RequestTicketDto;

@Service
public class RequestTicketServiceImpl implements RequestTicketService{

	private final RequestTicketRepository ticketRepository;
	
	public RequestTicketServiceImpl(RequestTicketRepository ticketRepository) {
		super();
		this.ticketRepository = ticketRepository;
	}

	@Override
	public RequestTicket save(RequestTicket ticket) {
		return ticketRepository.save(ticket);
	}

	@Override
	public RequestTicket findById(Long id) {
		return ticketRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteById(Long id) {
		ticketRepository.deleteById(id);
		
	}

	@Override
	public List<RequestTicket> findByRequester(UserInfo requester) {
		return ticketRepository.findByRequester(requester);
	}

	@Override
	public List<RequestTicket> findByHelper(UserInfo helper) {
		return ticketRepository.findByHelper(helper);
	}

	@Override
	public List<RequestTicket> findByCategory(Category category) {
		return ticketRepository.findByCategory(category);
	}

	@Override
	public List<RequestTicket> findByDrafted(boolean isDrafted) {
		return ticketRepository.findByDrafted(isDrafted);
	}

	@Override
	public List<RequestTicket> findByStatus(int status) {
		return ticketRepository.findByStatus(status);
	}

	@Override
	public RequestTicketDto convertToDto(RequestTicket ticket) {
		Status status = Status.fromInt(ticket.getStatus());
		System.out.println(status);
		System.out.println(status.name());
		System.out.println(status.toString());
		RequestTicketDto ticketDto = new RequestTicketDto(
				ticket.getId(), ticket.getRequester(), ticket.getHelper(), 
				ticket.getCategory().getMainCategory(), ticket.getCategory(), 
				ticket.getDetails(), ticket.getResponseFromHelper(),
				ticket.isDrafted(), status.getStatusCode());
		if(ticket.getCategory().getMainCategory() == null) {
			ticketDto.setMainCategory(ticket.getCategory());
			ticketDto.setSubCategory(null);
		}
		return ticketDto;
	}
	
	@Override
	public List<RequestTicketDto> convertToDtoList(List<RequestTicket> tickets) {
		List<RequestTicketDto> dto = new ArrayList<>();
		tickets.forEach(t -> {
			dto.add(convertToDto(t));
		});
		return dto;
	}


}
