package com.spring.mail.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
public class MailRequest 
{
	
	private String name;
	private String to;
	private String from;
	private String subject;
	
	
}