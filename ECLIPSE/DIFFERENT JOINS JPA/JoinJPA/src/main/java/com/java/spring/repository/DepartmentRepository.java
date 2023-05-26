package com.java.spring.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.java.spring.model.Department;
import com.java.spring.utility.CombinedEntity;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> 
{

	@Query("SELECT new com.java.spring.utility.CombinedEntity(d.deptName, e.empName, e.empMail, e.address)" + 
			"FROM Department d LEFT JOIN d.employees e")
	List<CombinedEntity> getJoinInformation();
	
	@Query("SELECT new com.java.spring.utility.CombinedEntity(d.deptName, e.empName, e.empMail, e.address)" + 
			"FROM Department d RIGHT JOIN d.employees e")
	List<CombinedEntity> getRightJoinInformation();
	
	@Query("SELECT new com.java.spring.utility.CombinedEntity(d.deptName, e.empName, e.empMail, e.address)" + 
			"FROM Department d INNER JOIN d.employees e")
	List<CombinedEntity> getInnerJoinInformation();
	
	@Query("SELECT new com.java.spring.utility.CombinedEntity(d.deptName, e.empName, e.empMail, e.address)" + 
			"FROM Department d CROSS JOIN Employee e")
	List<CombinedEntity> getCrossJoinInformation();

}
