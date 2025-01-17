package org.tsp.Banking_System.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tsp.Banking_System.dto.Management;
@Repository
public interface ManagementRepository extends JpaRepository<Management, Integer>
{
   Management findByEmail(String email);
}
