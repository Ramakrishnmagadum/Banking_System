package org.jsp.banking_system.dto;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Component
@Entity
public class Management {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
int id;
String email;
String password;
	
	
}
