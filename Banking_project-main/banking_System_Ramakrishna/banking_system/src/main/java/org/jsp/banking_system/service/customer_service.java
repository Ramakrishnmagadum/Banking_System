package org.jsp.banking_system.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.jsp.banking_system.Exception.MyException;
import org.jsp.banking_system.dto.Account;
import org.jsp.banking_system.dto.Customer;
import org.jsp.banking_system.dto.Login;
import org.jsp.banking_system.dto.Management;
import org.jsp.banking_system.helper.mailverification;
import org.jsp.banking_system.helper.responsive_structure;
import org.jsp.banking_system.repository.Management_repository;
import org.jsp.banking_system.repository.customer_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class customer_service {
	@Autowired
	customer_repository repository;
	
	@Autowired
	Customer customer;
	
	@Autowired
	Account account;
	
	@Autowired
	mailverification mail;

	public responsive_structure< Customer> save(Customer customer)
	{
		responsive_structure<Customer> structure=new responsive_structure<>();
//	  customer.getAge();
		int age=Period.between(customer.getDob().toLocalDate(), LocalDate.now()).getYears();
		if(age<18)
		{
			structure.setMessage("you should be 18+ to create the account");
			//CONFLICT -- is for not to save data but cheack condition and return some msg to user
			structure.setCode(HttpStatus.CONFLICT.value());
			structure.setData(customer);	
		}
		else {
			//for generating otp 
//		Math.random()*100000;-- one method
			//or we can use this also
			Random random=new Random();
			int otp=random.nextInt(100000,999999);
			customer.setOtp(otp);
			customer.setAge(age);
			
			mail.sendmail(customer);
			
			structure.setMessage("verification mail sent");
			structure.setCode(HttpStatus.PROCESSING.value());
			structure.setData(repository.save(customer));
		}
		return structure;
	}
	public responsive_structure<Customer> verify(int cust_id, int otp) throws MyException  {
		responsive_structure<Customer> structure=new responsive_structure<>();
		//optional is similer to collection but it is having only one set of data
		Optional<Customer> optional=repository.findById(cust_id);
		
		if(optional.isEmpty())
		{
			//through this we can handle but propery way is use exception
//			structure.setCode(HttpStatus.NOT_FOUND.value());
			throw new MyException("cheack id and try again");	
		}
		else {
			Customer customer=optional.get();
			if(customer.getOtp()==otp)
			{
				structure.setCode(HttpStatus.CREATED.value());
				structure.setMessage("account created succesfully");
				structure.setData(repository.save(customer));
				customer.setStatus(true);
				
			}else {
				throw new MyException("otp mismatch");			}
			}
		
		return structure;
	}
	
	
	
	//dont have idea
	public responsive_structure<Customer> login(Login login) throws MyException
	{
		responsive_structure<Customer> structure=new responsive_structure<>();
		 
		 Optional<Customer> optional=repository.findById(login.getId());
		 if(optional.isEmpty())
		 {
			 throw new MyException("invalid customer id");
		 }
		 else
		 {
			 Customer customer=optional.get();
			if(customer.getPassword().equals(login.getPassword())) 
			{
				if(customer.isStatus())
				{
					structure.setCode(HttpStatus.ACCEPTED.value());
					structure.setMessage("Login success");
					structure.setData(customer);
				}
				else
				{
					throw new MyException("verify your email first");
				}
			}
			else
			{
				throw new MyException("invalid password");
			}
		 }
		
			return  structure;
	}
	public responsive_structure<Account> CreateAccount(int cust_id, String type) throws MyException
	{
		responsive_structure<Account> structure=new responsive_structure<>();
		 Optional<Customer> optional=repository.findById(cust_id);
		 if(optional.isEmpty())
		 {
			 throw new MyException("invalid customer id");
		 }
		 else
		 {
			 Customer customer=optional.get();
			List<Account> list=customer.getAcconts();
			
			boolean flog=true;
			for(Account account:list)
			{
				if(account.getType().equals(type))
				{
					flog=false;
					break;
				}
			}
			if(!flog)
			{
				throw new MyException(type+"Accounts Allredy exits");
			}
			else
			{
			   account.setType(type);
			   if(type.equals("savings"))
			   {
				 account.setLimit(5000);
			   }
			   else
			   {
				   account.setLimit(10000);
			   }
			   
		    }
		 }
		
		return structure;
	}

}
