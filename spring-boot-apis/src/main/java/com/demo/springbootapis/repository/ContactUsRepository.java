package com.demo.springbootapis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.springbootapis.model.ContactUsInfo;

@Repository
public interface ContactUsRepository extends JpaRepository<ContactUsInfo, Integer>{
	
}
