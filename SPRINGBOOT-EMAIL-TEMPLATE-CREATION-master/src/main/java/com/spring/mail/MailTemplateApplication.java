package com.spring.mail;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.mail.dto.MailRequest;
import com.spring.mail.dto.MailResponse;
import com.spring.mail.service.EmailService;

@SpringBootApplication
@RestController
public class MailTemplateApplication {
	

		@Autowired
		private EmailService service;

		@PostMapping("/sendingEmail")
		public MailResponse sendEmail(@RequestBody MailRequest request) 
		{
			Map<String, Object> model = new HashMap<>();
			model.put("Name", request.getName());
			model.put("location", "TamilNadu,India");
			return service.sendEmail(request, model);

		}


	public static void main(String[] args) 
	{
		SpringApplication.run(MailTemplateApplication.class, args);
	}

}
