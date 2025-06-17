package com.clinic.appointment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clinic.appointment.model.masterPatient;
import com.clinic.appointment.model.patientPrescription;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class patientPrescriptionService  {
	
	@Autowired
	private patientPrescriptionRepo patientPrescriptionRepo;
	
	//@Autowired  
	//private HttpServletRequest request;
	
	public patientPrescription savePrescription(patientPrescription patientPrescription) {
		patientPrescriptionRepo.save(patientPrescription);
		return patientPrescription;
	}
	
	public patientPrescription getPrescription(patientPrescription patientPrescription) {
		List<patientPrescription> presc = patientPrescriptionRepo.findByPrescriptionId(patientPrescription.getPrescriptionId());
		return presc.get(0);
	}
	
	@Transactional
	public void deleteByPrescriptionId(patientPrescription patientPrescription) {
		patientPrescriptionRepo.deleteByPrescriptionId(patientPrescription.getPrescriptionId());
	}

}
