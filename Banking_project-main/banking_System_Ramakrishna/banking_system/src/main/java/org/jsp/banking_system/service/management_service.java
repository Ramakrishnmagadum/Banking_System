package org.jsp.banking_system.service;

import org.jsp.banking_system.dto.Management;
import org.jsp.banking_system.helper.responsive_structure;
import org.jsp.banking_system.repository.Management_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class management_service {
	
	@Autowired
	Management_repository repository;
	
	
	public responsive_structure< Management> save(Management management)
	{
		responsive_structure<Management> structure=new responsive_structure<>();
		structure.setCode(HttpStatus.CREATED.value());
		structure.setMessage("Account created succesfully");
		structure.setData(repository.save(management));
		return structure;
	}

}
