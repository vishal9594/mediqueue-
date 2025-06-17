package com.clinic.appointment.service;




import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinic.appointment.model.masterPatient;
import com.clinic.appointment.model.patientPrescription;
@Repository
public interface patientPrescriptionRepo extends JpaRepository<patientPrescription,Long> {
	
	//public List<patientPrescription> findByDate(LocalDate date);
//	public List<patientPrescription> findByPatientId(Long patientId);
	public List<patientPrescription> findByPrescriptionId(Long patientId);
	
	public void deleteByPrescriptionId(Long prescriptionId);
//	Optional<patientPrescription> findByEmail(String email);
}
