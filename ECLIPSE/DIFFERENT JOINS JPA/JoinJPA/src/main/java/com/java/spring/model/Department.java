package com.java.spring.model;

import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department 
{
	@Id
	private int deptId;
	private String deptName;
	private String description;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="deptId")
	private List<Employee> employees;


	public List<Employee> getEmployees()
	{
		return employees;
	}

	public void setEmployees(List<Employee> employees) 
	{
		this.employees = employees;
	}

	
}
