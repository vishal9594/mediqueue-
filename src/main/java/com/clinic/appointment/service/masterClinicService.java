package com.clinic.appointment.service;

import java.time.LocalDate;
import java.util.List;

import com.clinic.appointment.model.masterClinic;

public interface masterClinicService  {
	List<masterClinic> getDetails ();
	List<masterClinic> findByDate(LocalDate date);
	List<masterClinic> findByDateOrderByAppointmentseq(LocalDate date);
	public void updateIsDoneStatus(Long id,int isdone);
	public List<masterClinic> currentSeq(LocalDate date) ;
}
