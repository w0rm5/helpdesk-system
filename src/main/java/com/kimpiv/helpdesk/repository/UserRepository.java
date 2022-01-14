package com.kimpiv.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kimpiv.helpdesk.model.UserInfo;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {
	UserInfo findByEmail(String email);
	UserInfo findByPhone(String phone);
}
