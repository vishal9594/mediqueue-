package com.clinic.appointment.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import com.clinic.appointment.service.masterPatientService;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	masterPatientService masterPatientService;
	
	@Bean
	public UserDetailsService userDetailsService() {
		
		System.out.println(masterPatientService);
		return masterPatientService;
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		
		provider.setUserDetailsService(masterPatientService);
		return provider;
	}

	
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    	//abstractHttpConfigurer csrf
    	return httpSecurity.csrf().disable()
    			.formLogin(httpform->{
			httpform.loginPage("/userlogin").permitAll();
		}).authorizeHttpRequests(registry -> {
			registry.requestMatchers("/register","/bookAppointment","/assets/**","/","/getCurrentSeq","/verifyEmail",
					"/savePatient","/verifyOtp","/verifyOtp/**").permitAll();
			registry.anyRequest().authenticated();
			registry.shouldFilterAllDispatcherTypes(false); //default yes
		})
				.build();
    }

}

/*@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfiguration {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		//public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { securityFilterChain same bean name defined in class 
		//spring.main.allow-bean-definition-overriding=true
				
		
		return httpSecurity.formLogin(httpform->{
			httpform.loginPage("/login").permitAll();
		}).authorizeHttpRequests(registry -> {
			registry.requestMatchers("/register,/bookAppointment").permitAll();
			registry.anyRequest().authenticated();
		})
				.build();
	}
	
}
*/