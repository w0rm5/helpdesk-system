package com.kimpiv.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kimpiv.helpdesk.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
