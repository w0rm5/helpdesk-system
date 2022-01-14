package com.kimpiv.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kimpiv.helpdesk.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
	Category findByName(String name);
}
