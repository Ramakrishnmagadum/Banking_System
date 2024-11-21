package org.tsp.Banking_System.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.tsp.Banking_System.dto.BankAccount;
import org.tsp.Banking_System.dto.Customer;
import org.tsp.Banking_System.dto.Login;
import org.tsp.Banking_System.dto.Management;
import org.tsp.Banking_System.exception.MyException;
import org.tsp.Banking_System.helper.ResponceStructure;
import org.tsp.Banking_System.repository.BankRepository;
import org.tsp.Banking_System.repository.ManagementRepository;

@Service
public class ManagementService 
{
	@Autowired
	BankRepository bankRepository;
	
		
	@Autowired
	ManagementRepository repository;
	
	
	//for adding management data
  public ResponceStructure<Management> save(Management management)
  {
    ResponceStructure< Management> structure=new ResponceStructure<>();
    structure.setCode(HttpStatus.CREATED.value());
    structure.setData(repository.save(management));
    structure.setMsg("Data created Successfully");
    return structure;  
  }
  
  
  
  
  //for login the management account
  public ResponceStructure<Management> login(Management management) throws MyException
  {
  	 ResponceStructure<Management> structure=new ResponceStructure<>();
  	 
  	 Management management1=repository.findByEmail(management.getEmail());
  	 if(management1==null)
  	 {
  		 throw new MyException("invalid Management id");
  	 }
  	 else
  	 {
  		
  		if(management1.getPassword().equals(management.getPassword())) 
  		{
  			
  				structure.setCode(HttpStatus.ACCEPTED.value());
  				structure.setMsg("Login success");
  				structure.setData(management1);
  				
  		}
  			else
  			{
  				throw new MyException("invalid password");
  			}
  		}
  		
  	 
  	
  		return  structure;
  }
  
  
  
  //fetch all customer accounts at same time
public ResponceStructure<BankAccount> fetchAllAccounts() throws MyException {
	ResponceStructure<List<BankAccount>> structure=new ResponceStructure<>();
	List<BankAccount> list=bankRepository.findAll();
	
	if(list.isEmpty())
	{
		throw new MyException("no account found");
	}
	else {
		structure.setCode(HttpStatus.FOUND.value());
			structure.setMsg("Data found");
			structure.setData(list);
	}
	return null;
}



//customer account approved and approved given back
//if approved not given then it give to approved else it return back approved
public ResponceStructure<BankAccount> changeStatus(long acno) {
	ResponceStructure<BankAccount> structure=new ResponceStructure<BankAccount>();
	  Optional<BankAccount> optional   =bankRepository.findById(acno);
	  BankAccount account=optional.get();
	  if(account.isStatus())
	  {
		  account.setStatus(false);
	  }else {
		  account.setStatus(true);
	  }
	structure.setCode(HttpStatus.OK.value());
	structure.setMsg("changed status");
	structure.setData(bankRepository.save(account));
	
	return structure;
}
}









