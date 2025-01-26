package com.clinic.appointment.model;





import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity(name = "patient_prescription")
@AllArgsConstructor
public class patientPrescription {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="patient_id")
	Long patientId;
	
	@Column(name="prescription_id")
	Long prescriptionId;
	
	@Lob
    @Column(name = "patient_prescription", columnDefinition="longblob")
	byte[] patientPrescription;
	
	
	@Column(name="date_added", columnDefinition = "DATE")
	LocalDate dateAdded;
	
	@Column(name="description")
	String description;

	
	

	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public Long getPrescriptionId() {
		return prescriptionId;
	}

	public void setPrescriptionId(Long prescriptionId) {
		this.prescriptionId = prescriptionId;
	}

	public byte[] getPatientPrescription() {
		return patientPrescription;
	}

	public void setPatientPrescription(byte[] patientPrescription) {
		this.patientPrescription = patientPrescription;
	}

	public LocalDate getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(LocalDate dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "patient_prescription [id=" + id + ", patient_id=" + patientId + ", prescription_id=" + prescriptionId
				+ ", patient_prescription=" + Arrays.toString(patientPrescription) + ", date_added=" + dateAdded
				+ ", description=" + description + "]";
	}
	
	
	
	
	
	
}
