package com.clinic.appointment.service;




import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinic.appointment.model.guestDetails;
import com.clinic.appointment.model.masterClinic;
@Repository("guest_details")
public interface guestDetailsRepo extends JpaRepository<guestDetails,Long> {
	
	
}
