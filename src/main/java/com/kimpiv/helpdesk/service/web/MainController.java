package com.kimpiv.helpdesk.service.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kimpiv.helpdesk.model.Category;
import com.kimpiv.helpdesk.model.RequestTicket;
import com.kimpiv.helpdesk.model.Status;
import com.kimpiv.helpdesk.model.TicketExcelExport;
import com.kimpiv.helpdesk.model.UserInfo;
import com.kimpiv.helpdesk.service.CategoryService;
import com.kimpiv.helpdesk.service.RequestTicketService;
import com.kimpiv.helpdesk.service.UserService;
import com.kimpiv.helpdesk.service.web.dto.RequestTicketDto;

@Controller
@RequestMapping("")
public class MainController {

	private final RequestTicketService requestTicketService;
	private final UserService userService;
	private final CategoryService categoryService;

	public MainController(RequestTicketService requestTicketService, UserService userService,
			CategoryService categoryService) {
		super();
		this.requestTicketService = requestTicketService;
		this.userService = userService;
		this.categoryService = categoryService;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	private UserInfo getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userService.getUserByEmail(auth.getName());
	}
	
	private boolean checkCurrentUserRole(String role) {
		return SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(role));
	}
	
	@GetMapping("/")
	public String home(Model model) {
		UserInfo user = getCurrentUser();
		List<RequestTicket> tickets;
		if(checkCurrentUserRole("ROLE_USER")){
			tickets = requestTicketService.findByRequester(user);
		}else {
			tickets = new ArrayList<RequestTicket>();
			tickets.addAll(requestTicketService.findByHelperNullAndDraftedFalse());
			tickets.addAll(requestTicketService.findByHelper(user));
			Collections.sort(tickets, (t1, t2) -> t1.getDate().compareTo(t2.getDate()));
		}
		model.addAttribute("statuses", Status.getIntStringMap());
		model.addAttribute("tickets", requestTicketService.convertToDtoList(tickets));
		return "index";
	}
	
	@GetMapping("ticket/{id}")
	public String getTicket(@PathVariable("id") String id, Model model) {
		model.addAttribute("mainCategories", categoryService.findSubCategories(null));
		model.addAttribute("statuses", Status.getIntStringMap());
		if(id.equals("new")) {
			model.addAttribute("ticket", new RequestTicketDto(getCurrentUser()));
		} else {
			try {				
				Long ticketId = Long.parseLong(id);
				RequestTicket ticket = requestTicketService.findById(ticketId);
				if(ticket == null) {
					model.addAttribute("status", "404");
					model.addAttribute("error", "Not Found");
					return "error";
				}
				model.addAttribute("ticket", requestTicketService.convertToDto(ticket));
			} catch (NumberFormatException e) {
				model.addAttribute("status", "404");
				model.addAttribute("error", "Not Found");
				return "error";
			}
		}
		return "ticket_edit";
	}
	
	@PostMapping("ticket/save")
	public String saveTicket(@ModelAttribute("ticket") RequestTicketDto ticket) {	
		Category category = ticket.getSubCategory() != null ? ticket.getSubCategory() : ticket.getMainCategory();
		RequestTicket tic;
		if(ticket.getId() == null) {
			tic = new RequestTicket(
					ticket.getRequester(), ticket.getHelper(), category, 
					ticket.getDetails(), ticket.getResponseFromHelper(), 
					ticket.isDrafted(), ticket.getStatus()
				);
		}else {
			tic = requestTicketService.findById(ticket.getId());
			if(checkCurrentUserRole("ROLE_HELPER")) {
				if(ticket.getHelper() == null) {
					ticket.setHelper(getCurrentUser());
				}
			}
			tic.setCategory(category);
			tic.setDetails(ticket.getDetails());
			tic.setDrafted(ticket.isDrafted());
			tic.setRequester(ticket.getRequester());
			tic.setHelper(ticket.getHelper());
			tic.setResponseFromHelper(ticket.getResponseFromHelper());
			tic.setStatus(ticket.getStatus());
		}
		requestTicketService.save(tic);
		return "redirect:/?" + (tic.isDrafted() ? "drafted" : "saved");
	}
	
	@GetMapping("ticket/search")
	@ResponseBody
	public List<RequestTicketDto> searchList(Model model, 
			@RequestParam(name = "status") int status,
			@RequestParam(name = "date") String date) {
		if(date.isBlank()) {
			return requestTicketService.convertToDtoList(
					requestTicketService.findByHelperAndStatusAndDate(getCurrentUser(), status, null)
				);
		}
		return requestTicketService.convertToDtoList(
				requestTicketService.findByHelperAndStatusAndDate(getCurrentUser(), status, LocalDate.parse(date))
			);
	}
	
	@GetMapping("/ticket/export/excel")
	public void exportToExcel(HttpServletResponse response, 
			@RequestParam(name = "status") int status,
			@RequestParam(name = "date") String date) throws IOException {
		
		response.setContentType("application/octet-stream");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        List<RequestTicket> ticketList;
        if(date.isBlank()) {
        	ticketList = requestTicketService.findByHelperAndStatusAndDate(getCurrentUser(), status, null);
        } else {
        	ticketList = requestTicketService.findByHelperAndStatusAndDate(getCurrentUser(), status, LocalDate.parse(date));
        }
        TicketExcelExport exporter = new TicketExcelExport(ticketList);
        exporter.export(response);
	}

}
