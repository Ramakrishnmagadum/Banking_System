package org.jsp.banking_system.dto;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
@Component
public class Account 
{
	@Id
	@GeneratedValue(generator = "A/Cno")
	@SequenceGenerator(name = "A/Cno",sequenceName = "A/Cno",initialValue = 10210001,allocationSize = 1)
    long number;
	String type;
	double limit;
	double amount;
	boolean status;
	
}