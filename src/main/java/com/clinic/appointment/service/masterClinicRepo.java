package com.clinic.appointment.service;




import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clinic.appointment.model.masterClinic;
@Repository
public interface masterClinicRepo extends JpaRepository<masterClinic,Long> {
	
	public List<masterClinic> findByDate(LocalDate date);
	public List<masterClinic> findByDateOrderByAppointmentseqDesc(LocalDate date);
//	select * from masterclinic where id =(select max(id) from masterclinic where isdone > 0) and date='2024-09-29';
//	public masterClinic findCurrentSeq(LocalDate date);
	
	public List<masterClinic> findTopByDateAndIsdoneGreaterThanOrderByAppointmentseqDesc(LocalDate date,int status);
	public List<masterClinic> findTopByDateAndIsdoneGreaterThanOrderByAppointmentseqAsc(LocalDate date,int status);
	
}
