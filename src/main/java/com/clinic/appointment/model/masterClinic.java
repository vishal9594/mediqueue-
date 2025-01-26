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
@Entity(name = "masterclinic")
@AllArgsConstructor
public class masterClinic {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	
	@Column(name="patient_id")
	Long patient_id;
	
	@Column(name="patient_name")
	String patient_name;
	@Column(name="date", columnDefinition = "DATE")
	LocalDate date;
	@Column(name="appointment_seq")
	int appointmentseq;
	@Column(name="isdone")
	int isdone;
	
	String remarks;
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPatient_id() {
		return patient_id;
	}
	public void setPatient_id(Long patient_id) {
		this.patient_id = patient_id;
	}
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public int getAppointmentseq() {
		return appointmentseq;
	}
	public void setAppointmentseq(int appointmentseq) {
		this.appointmentseq = appointmentseq;
	}
	public int getIsdone() {
		return isdone;
	}
	public void setIsdone(int isdone) {
		this.isdone = isdone;
	}
	@Override
	public String toString() {
		return "masterClinic [id=" + id + ", patient_id=" + patient_id + ", patient_name=" + patient_name + ", date="
				+ date + ", appointmentseq=" + appointmentseq + ", isdone=" + isdone + "]";
	}
	
	
}
