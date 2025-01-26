package com.clinic.appointment.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//@Configuration
public class ApplicationConfig extends WebMvcConfigurationSupport{

	
	@Override
	protected void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		// TODO Auto-generated method stub
		//super.configureAsyncSupport(configurer);
		configurer.setDefaultTimeout(5000);
		configurer.setTaskExecutor(mcvtaskEx());
	}
	
	
	@Bean
	public AsyncTaskExecutor mcvtaskEx() {
		ThreadPoolTaskExecutor poolTask = new ThreadPoolTaskExecutor();
		poolTask.setThreadNamePrefix("hplus-thread-");
		return poolTask;
	}
}
