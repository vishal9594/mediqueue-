package com.clinic.appointment.model;





import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity(name = "guest_details")
@AllArgsConstructor
public class guestDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Column(name="master_id")
	Long masterId;
	
	@Column(name="mobile")
	String mobile;
	@Column(name="email")
	String email;
	@Column(name="date", columnDefinition = "DATE")
	LocalDate date;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMasterId() {
		return masterId;
	}
	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
		
	
}
