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
@Entity(name = "masterpatient")
@AllArgsConstructor
public class masterPatient {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="patient_id")
	Long patient_id;
	
	@Column(name="name")
	String name;
	@Column(name="mobile")
	String mobile;
	@Column(name="email")
	String email;
	@Column(name="password")
	String password;
	@Column(name="date", columnDefinition = "DATE")
	LocalDate date;
	@Column(name="modified_date", columnDefinition = "DATE")
	LocalDate modified_date;
	@Column(name="dob", columnDefinition = "DATE")
	LocalDate dob;
	@Column(name="gender")
	String gender;
	@Column(name="status")
	Integer status;
	@Column(name="isverified")
	Integer isVerified;
	@Column(name="OTP")
	String otp;
	@Column(name="otpverifyattempt" ,columnDefinition= "default 0")
	Integer otpverifyattempt;
	
	
	
	public Integer getOtpverifyattempt() {
		return otpverifyattempt;
	}
	public void setOtpverifyattempt(Integer otpverifyattempt) {
		this.otpverifyattempt = otpverifyattempt;
	}
	public Integer getIsVerified() {
		return isVerified;
	}
	public void setIsVerified(Integer isVerified) {
		this.isVerified = isVerified;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LocalDate getModified_date() {
		return modified_date;
	}
	public void setModified_date(LocalDate modified_date) {
		this.modified_date = modified_date;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getPatient_id() {
		return patient_id;
	}
	public void setPatient_id(Long patient_id) {
		this.patient_id = patient_id;
	}
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "masterPatient [patient_id=" + patient_id + ", name=" + name + ", mobile=" + mobile + ", email=" + email
				+ ", password=" + password + ", date=" + date + ", modified_date=" + modified_date + ", dob=" + dob
				+ ", status=" + status + ", gender=" + gender + "]";
	}
	
	
	
	
}
