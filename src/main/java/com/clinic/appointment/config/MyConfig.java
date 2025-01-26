package com.clinic.appointment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "myapp")
public class MyConfig {

    private String serverUrl;
    private int maxThreads;
    
	public String getServerUrl() {
		return serverUrl;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public int getMaxThreads() {
		return maxThreads;
	}
	public void setMaxThreads(int maxThreads) {
		this.maxThreads = maxThreads;
	}

    
    
}
