package org.jsp.banking_system.repository;

import org.jsp.banking_system.dto.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface customer_repository extends  JpaRepository<Customer, Integer>
{
	

}
