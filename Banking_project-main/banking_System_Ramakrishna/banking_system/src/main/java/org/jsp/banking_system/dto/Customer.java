package org.jsp.banking_system.dto;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
@Component
public class Customer {
	@Id
	
	//@SequenceGenerator--for serial wise gented--and  initialValue = 41462012 --start from here-----allocationSize = 1 thus is difference between sizes
	
	@SequenceGenerator(initialValue = 41462012,allocationSize = 1,name="cust_id" ,sequenceName = "cust_id")

	//	generator = "cust_id" andsequenceName = "cust_id" is same name
	
	@GeneratedValue(generator = "cust_id")
	int cust_id;
	String name;
	String email;
	String password;
	long mobile;
	Date dob;
	int age;
	boolean status;
	int otp;
	
	

}
