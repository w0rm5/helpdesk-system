package com.kimpiv.helpdesk.repository;

import java.util.List;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kimpiv.helpdesk.model.Category;
import com.kimpiv.helpdesk.model.RequestTicket;
import com.kimpiv.helpdesk.model.UserInfo;

public interface RequestTicketRepository extends JpaRepository<RequestTicket, Long>{
	List<RequestTicket> findByRequester(UserInfo requester);
	List<RequestTicket> findByHelper(UserInfo helper);
	List<RequestTicket> findByCategory(Category category);
	List<RequestTicket> findByDrafted(boolean isDrafted);
	List<RequestTicket> findByStatus(int status);
}
