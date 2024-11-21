package org.jsp.banking_system.controller;

import org.jsp.banking_system.dto.Management;
import org.jsp.banking_system.helper.responsive_structure;
import org.jsp.banking_system.service.management_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("management")
public class managament_controller {

	@Autowired
	management_service service;
	
	@PostMapping("add")
	public responsive_structure<Management> save(@RequestBody Management management)
	{
		return service.save(management);
		
	}
	
}
