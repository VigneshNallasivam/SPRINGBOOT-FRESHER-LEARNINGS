package com.spring.mail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Configuration
@Data
public class ApiConfig 
{
	
	@Primary
	@Bean 
	public FreeMarkerConfigurationFactoryBean factoryBean() 
	{
		FreeMarkerConfigurationFactoryBean bean=new FreeMarkerConfigurationFactoryBean();
		bean.setTemplateLoaderPath("classpath:/templates");
		return bean;
	}

}