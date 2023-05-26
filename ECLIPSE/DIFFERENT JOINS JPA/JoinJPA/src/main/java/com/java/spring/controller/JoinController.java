package com.java.spring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.java.spring.utility.*;
import com.java.spring.dto.ResponseDto;
import com.java.spring.model.Department;
import com.java.spring.model.Employee;
import com.java.spring.service.JoinQueryService;

@RestController
public class JoinController
{
	@Autowired
	private JoinQueryService service;

	@PostMapping("/addEmployee")
	public ResponseEntity<ResponseDto> addEmployee(@RequestBody Employee employee) {
		Employee employeeResponse = service.addEmployee(employee);
		ResponseDto response = new ResponseDto("employee added", employeeResponse);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/viewAllEmployees")
	public ResponseEntity<ResponseDto> viewEmployees() {
		List<Employee> employees = service.viewAllEmployees();
		ResponseDto response = new ResponseDto("List of Employees ", employees);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/addDepartment")
	public ResponseEntity<ResponseDto> addEmployee(@RequestBody Department department) {
		Department departmentResponse = service.addDepartmentWithEmployee(department);
		ResponseDto response = new ResponseDto("department with employee added", departmentResponse);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/viewAllDepartments")
	public ResponseEntity<ResponseDto> viewDepartmentsWithEmployee() {
		List<Department> departments = service.viewAllDepartmentWithEmployee();
		ResponseDto response = new ResponseDto("List of departments ", departments);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getLeftJoin")
	public ResponseEntity<ResponseDto> getLeftJoin() {
		List<CombinedEntity> getJoinInfo = service.getLeftJoinInfo();
		ResponseDto response = new ResponseDto("List of Join Info", getJoinInfo);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getRightJoin")
	public ResponseEntity<ResponseDto> getRightJoin() {
		List<CombinedEntity> getRightJoinInfo = service.getRightJoinInfo();
		ResponseDto response = new ResponseDto("List of Right Join Info", getRightJoinInfo);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getInnerJoin")
	public ResponseEntity<ResponseDto> getInnerJoin() {
		List<CombinedEntity> getInnerJoinInfo = service.getInnerJoinInfo();
		ResponseDto response = new ResponseDto("List of Inner Join Info", getInnerJoinInfo);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getCrossJoin")
	public ResponseEntity<ResponseDto> getCrossJoin() {
		List<CombinedEntity> getCrossJoin = service.getCrossJoinInfo();
		ResponseDto response = new ResponseDto("List of Cross Join Info", getCrossJoin);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
