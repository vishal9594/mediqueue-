package com.clinic.appointment.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

	@ExceptionHandler(value=ApplicationException.class)
	public String handleException() {
		
		return "global exception";
	}
}
