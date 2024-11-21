package org.tsp.Banking_System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tsp.Banking_System.dto.BankAccount;


@Repository
public interface BankRepository  extends JpaRepository<BankAccount, Long>{
	

}
