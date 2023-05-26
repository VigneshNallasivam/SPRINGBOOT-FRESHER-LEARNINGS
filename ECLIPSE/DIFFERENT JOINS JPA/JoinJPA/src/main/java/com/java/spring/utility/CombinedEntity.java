package com.java.spring.utility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CombinedEntity 
{
	private String empDept;
	private String empName;
	private String empEmail;
	private String empAddress;
}
