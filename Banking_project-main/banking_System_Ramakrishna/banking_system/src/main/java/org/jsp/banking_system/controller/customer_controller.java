package org.jsp.banking_system.controller;


import org.jsp.banking_system.Exception.MyException;
import org.jsp.banking_system.dto.Account;
import org.jsp.banking_system.dto.Customer;
import org.jsp.banking_system.dto.Login;
import org.jsp.banking_system.dto.Management;
import org.jsp.banking_system.helper.responsive_structure;
import org.jsp.banking_system.service.customer_service;
import org.jsp.banking_system.service.management_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customer")
public class customer_controller {
	
	@Autowired
	customer_service service;
	
	
	
	@PostMapping("add")
	public responsive_structure<Customer> save(@RequestBody Customer customer)
	{
		return service.save(customer);
		
	}
	
	
	//for otp verification
	@PutMapping("otp/{cust_id}/{otp}")
public responsive_structure<Customer> otpverify(@PathVariable int cust_id, @PathVariable int otp) throws MyException
	{
		return service.verify(cust_id , otp);
		
	}
	
	
	@PostMapping("login")
	public responsive_structure<Customer> login(@RequestBody Login login) throws MyException
	{
		return service.login(login);
	}
	
	@PostMapping("account/{cust_id}/{type}")
	public responsive_structure<Account> CreateAccount(@PathVariable int cust_id,@PathVariable String type) throws MyException
	{
		return service.CreateAccount(cust_id,type);
	}

}
