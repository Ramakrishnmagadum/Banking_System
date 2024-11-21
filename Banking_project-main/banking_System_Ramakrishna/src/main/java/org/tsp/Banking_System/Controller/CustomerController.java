package org.tsp.Banking_System.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tsp.Banking_System.dto.BankAccount;
import org.tsp.Banking_System.dto.BankTransaction;
import org.tsp.Banking_System.dto.Customer;
import org.tsp.Banking_System.dto.Login;
import org.tsp.Banking_System.exception.MyException;
import org.tsp.Banking_System.helper.ResponceStructure;
import org.tsp.Banking_System.service.CustomerService;
import org.yaml.snakeyaml.error.MarkedYAMLException;

@RestController
@RequestMapping("customer")
public class CustomerController 
{
	@Autowired
	CustomerService service;
	 
	@PostMapping("add")
	public ResponceStructure<Customer> save(@RequestBody Customer customer)
	{
		return service.save(customer);
	}
	
	
	
	
	//for otp verification
	@PutMapping("otp/{cust_id}/{otp}")
	public ResponceStructure<Customer> OtpVerification(@PathVariable int cust_id,@PathVariable int otp) throws MyException
	{
		return service.OtpVerification(cust_id,otp);
	}
	
	
	
	//login customer account 
	@PostMapping("login")
	public ResponceStructure<Customer> login(@RequestBody Login login) throws MyException
	{
		return service.login(login);
	}
	
	
	//create account of the customer
	@PostMapping("account/{cust_id}/{type}")
	public ResponceStructure<Customer> CreateAccount(@PathVariable int cust_id,@PathVariable String type) throws MyException
	{
		return service.CreateAccount(cust_id,type);
	}
	
	
	//customer view account
	@GetMapping("accounts/{cust_id}")
	public ResponceStructure<List<BankAccount>> fetchAllTrue(@PathVariable int cust_id) throws MyException
	{
		return service.fetchAllTrue(cust_id);
	}
	
	
	
	//cheacking balance
	@GetMapping("account/cheack/{acno}")
	public ResponceStructure<Double> cheackBalance(long acno)
	{
		return service.cheackBalance(acno);
	}
	
	
	
	//to deposite amount into bankaccount
	@PutMapping("account/deposit/{acno}/{amount}")
	public ResponceStructure<BankAccount> deposite(@PathVariable long acno, @PathVariable double amount)
	{
		return service.deposite(acno, amount);
	}
	
	@PutMapping("account/withdraw/{acno}/{amount}")
	public ResponceStructure<BankAccount> withdraw(@PathVariable long acno, @PathVariable double amount) throws MyException
	{
		return service.withdraw(acno, amount);
	}
	
	
	@GetMapping("account/viewtransaction/{acno}")
	public  ResponceStructure<List<BankTransaction>> viewtransaction(@PathVariable long acno) throws MyException
	{
		return service.viewtransaction(acno);
	}
}
