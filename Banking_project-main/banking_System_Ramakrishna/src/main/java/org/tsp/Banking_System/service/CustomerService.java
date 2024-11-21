package org.tsp.Banking_System.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.tsp.Banking_System.dto.BankAccount;
import org.tsp.Banking_System.dto.BankTransaction;
import org.tsp.Banking_System.dto.Customer;
import org.tsp.Banking_System.dto.Login;
import org.tsp.Banking_System.exception.MyException;
import org.tsp.Banking_System.helper.MailVarification;
import org.tsp.Banking_System.helper.ResponceStructure;
import org.tsp.Banking_System.repository.BankRepository;
import org.tsp.Banking_System.repository.CustomerRepository;

@Service
public class CustomerService 
{
 @Autowired
 CustomerRepository repository;
 
 @Autowired
 Customer customer;
 
 @Autowired
 MailVarification mail;
 
 @Autowired
 BankAccount account;
 
 @Autowired
 BankRepository bankRepository;
 
 @Autowired
 BankTransaction bankTransaction;
 
 //for creating customer account-- 1 step
 public ResponceStructure<Customer> save(Customer customer)
 {
	 ResponceStructure<Customer> structure=new ResponceStructure<>();
	 int age=Period.between(customer.getDob().toLocalDate(), LocalDate.now()).getYears(); 
	 if(age<18)
	 {
		 structure.setMsg(("You Should Be 18+ to Create The Account"));
		 //CONFLIT--->is not for save the data check condition and return some msg to the user
		 structure.setCode(HttpStatus.CONFLICT.value());
		 structure.setData(customer);
	 }
	 else
	 {
		 //for generating otp-->Math.random()*100000;  --->one method    (or)
		 //we can write this also 
		 Random random=new Random();
		 int otp=random.nextInt(100000,999999);
		 customer.setOtp(otp);
		 customer.setAge(age);
		 
		 mail.sendmail(customer);
		 
		 structure.setMsg("verification mail sent");
		 structure.setCode(HttpStatus.PROCESSING.value());
		 structure.setData(repository.save(customer));
		
		 
		 
	 }
	 return structure;
 }
 
 
 
 //otp verification 
 public ResponceStructure<Customer> OtpVerification(int cust_id,int otp)throws MyException
 {

	 ResponceStructure<Customer> structure=new ResponceStructure<>();
	 //Optional is similar to the collection but it is having only one set of data
	 Optional<Customer> optional=repository.findById(cust_id);
	 if(optional.isEmpty())
	 {
		 throw new MyException("check id and try again");
	 }
	 else
	 {
		 Customer customer=optional.get();
		 if(customer.getOtp()==otp)
		 {
			 customer.setStatus(true);
			 structure.setCode(HttpStatus.CREATED.value());
			 structure.setMsg("account created successfully");
			 structure.setData(repository.save(customer));
			 
		 }
		 else
		 {
			 throw new MyException("otp mismatch");
		 }
		 return structure;
	 }
	 
 }
 
 
 
 
 //for login into customer account
public ResponceStructure<Customer> login(Login login) throws MyException
{
	 ResponceStructure<Customer> structure=new ResponceStructure<>();
	 
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
				structure.setMsg("Login success");
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



//its for create the account and after creatig acccount succesfully----- i will wait for management side account approved
public ResponceStructure<Customer> CreateAccount(int cust_id, String type) throws MyException
{
	ResponceStructure<Customer> structure=new ResponceStructure<Customer>();
	 Optional<Customer> optional=repository.findById(cust_id);
	 if(optional.isEmpty())
	 {
		 throw new MyException("invalid customer id");
	 }
	 else
	 {
		 Customer customer=optional.get();
		List<BankAccount> list=customer.getAcconts();
		
		boolean flog=true;
		for(BankAccount account:list)
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
			 account.setBanklimit(5000);
		   }
		   else
		   {
			   account.setBanklimit(10000);
		   }
		   
		   
		    list.add(account);
			customer.setAcconts(list);
	    }
		structure.setCode(HttpStatus.ACCEPTED.value());
		structure.setMsg("Account created wait for management approved");
		structure.setData(repository.save(customer));
	 }
	return structure;
}





//view the customer account details
public ResponceStructure<List<BankAccount>> fetchAllTrue(int cust_id) throws MyException {
	ResponceStructure<List<BankAccount>> structure=new ResponceStructure<List<BankAccount>>();
  	 Optional<Customer> optional=repository.findById(cust_id);
  	 
  	 Customer customer=optional.get();
  	 
  	 List<BankAccount> list=customer.getAcconts();
  	 
  	 List<BankAccount> res=new ArrayList<BankAccount>();
  	 for(BankAccount account:list)
  	 {
  		 if(account.isStatus())
  		 {
  			res.add(account);
  		 }
  	 }
  		 if(res.isEmpty())
  		 {
  	  			 throw new MyException("no active account found");
  	  		 
  		 }else {
  			 structure.setCode(HttpStatus.FOUND.value());
  			 structure.setMsg("Account found");
  			 structure.setData(res);
  		 }
	return structure;
}



public ResponceStructure<Double> cheackBalance(long acno) {
	ResponceStructure<Double> structure=new ResponceStructure<Double>();
	
	Optional<BankAccount> optional=bankRepository.findById(acno);
			BankAccount account=optional.get();
	
	structure.setCode(HttpStatus.FOUND.value());
	structure.setMsg("data found");
	structure.setData(account.getAmount());
	
	
	return structure;
}



//deposit the amount
public ResponceStructure<BankAccount> deposite(long acno, double amount)
{
	ResponceStructure<BankAccount> structure=new ResponceStructure<BankAccount>();
	BankAccount account=bankRepository.findById(acno).get();
	account.setAmount(account.getAmount()+amount);
	
bankTransaction.setDatetime(LocalDate.now());
bankTransaction.setDeposite(amount);
bankTransaction.setBalance(account.getAmount());

List<BankTransaction> transactions= account.getBankTransaction();
transactions.add(bankTransaction);



structure.setCode(HttpStatus.ACCEPTED.value());
structure.setMsg("ammount added succesfully");
structure.setData(bankRepository.save(account));
	

return structure;
	
}



public ResponceStructure<BankAccount> withdraw(long acno, double amount) throws MyException {
	
	
	ResponceStructure<BankAccount> structure=new ResponceStructure<BankAccount>();
	BankAccount account=bankRepository.findById(acno).get();
	if(amount>account.getBanklimit())
	{
		throw new MyException("out of limit");
	}else {
		if(amount>account.getAmount()) {
			throw new MyException("insuficient found");
		}else {
		account.setAmount(account.getAmount()- amount);
		

	
	
	
	
	
bankTransaction.setDatetime(LocalDate.now());
bankTransaction.setWithdraw(amount);
bankTransaction.setBalance(account.getAmount());

List<BankTransaction> transactions= account.getBankTransaction();
transactions.add(bankTransaction);



structure.setCode(HttpStatus.ACCEPTED.value());
structure.setMsg("ammount withdraw succesfully");
structure.setData(bankRepository.save(account));
		}
	}

return structure;
}



public ResponceStructure<List<BankTransaction>> viewtransaction(long acno) throws MyException
{
	ResponceStructure<List<BankTransaction>> structure=new ResponceStructure<List<BankTransaction>>();
	
	BankAccount account=bankRepository.findById(acno).get();
	List<BankTransaction> list=account.getBankTransaction();
	if(list.isEmpty())
	{
		throw new MyException("no transaction");
		
	}else {
		structure.setCode(HttpStatus.FOUND.value());
		structure.setMsg("data found ");
		structure.setData(list);
			
	}
	
	return structure;
}
}




