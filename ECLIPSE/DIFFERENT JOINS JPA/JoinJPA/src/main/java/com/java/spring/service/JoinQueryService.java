package com.java.spring.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.java.spring.model.Department;
import com.java.spring.model.Employee;
import com.java.spring.repository.DepartmentRepository;
import com.java.spring.repository.EmployeeRepository;
import com.java.spring.utility.CombinedEntity;

@Service
public class JoinQueryService
{
	@Autowired
	DepartmentRepository deptRepo;

	@Autowired
	EmployeeRepository empRepo;
	
	public Employee addEmployee(Employee employee) 
	{
		return empRepo.save(employee);
	}

	public List<Employee> viewAllEmployees() 
	{
		return empRepo.findAll();
	}

	public Department addDepartmentWithEmployee(Department department) 
	{
		return deptRepo.save(department);
	}

	public List<Department> viewAllDepartmentWithEmployee() 
	{
		return deptRepo.findAll();
	}

	public List<CombinedEntity> getLeftJoinInfo() 
	{
		return deptRepo.getJoinInformation();
	}

	public List<CombinedEntity> getRightJoinInfo()
	{
		return deptRepo.getRightJoinInformation();
	}	
	
	public List<CombinedEntity> getInnerJoinInfo() 
	{
		return deptRepo.getInnerJoinInformation();
	}

	public List<CombinedEntity> getCrossJoinInfo() 
	{
		return deptRepo.getCrossJoinInformation();
	}

	
}
