package com.java.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.java.spring.model") 
@EnableJpaRepositories("com.java.spring.repository")
public class JoinJpaApplication 
{

	public static void main(String[] args) 
	{
		SpringApplication.run(JoinJpaApplication.class, args);
	}

}
