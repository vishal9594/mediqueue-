package com.clinic.appointment.service;




import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinic.appointment.model.masterClinic;
import com.clinic.appointment.model.masterPatient;
@Repository
public interface masterPatientRepo extends JpaRepository<masterPatient,Long> {
	
	public List<masterPatient> findByDate(LocalDate date);
	
	Optional<masterPatient> findByEmail(String email);
//	Optional<masterPatient> findByOtpAndId(String Otp,Long patient_id);
}
