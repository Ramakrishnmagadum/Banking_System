package org.jsp.banking_system.Exception;

import org.jsp.banking_system.helper.responsive_structure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
//if u handle thorough this we get in proper manner
public class Exception_controller {


		@ExceptionHandler(value = MyException.class)
		public ResponseEntity<responsive_structure<String>> idNotFound(MyException ie) {
			responsive_structure<String> responseStructure = new responsive_structure<String>();
			responseStructure.setCode(HttpStatus.NOT_FOUND.value());
			responseStructure.setMessage("Request failed");
			responseStructure.setData(ie.getMessage());
			return new ResponseEntity<responsive_structure<String>>(responseStructure, HttpStatus.NOT_ACCEPTABLE);


	}
		}



