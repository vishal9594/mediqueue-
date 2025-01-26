package com.clinic.appointment.service;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinic.appointment.model.guestDetails;
import com.clinic.appointment.model.masterClinic;
import com.clinic.appointment.model.masterPatient;

@Service
public class masterClinicServiceImpl implements masterClinicService {
	@Autowired
	masterClinicRepo masterClinicRepoObj;
	@Autowired
	guestDetailsRepo guestDetailsRepoObj;
	@Autowired
	masterPatientRepo masterPatientRepoObj;
	@Override
	public List<masterClinic> getDetails() {
		// TODO Auto-generated method stub
	List<masterClinic> allData=masterClinicRepoObj.findAll();
	/*Date date = new Date();
	System.out.println(date);*/
	
	
	
	System.out.println("all data");
	System.out.println(allData);
		return allData;
	}
	@Override
	public List<masterClinic> findByDate(LocalDate date) {
		List<masterClinic> masterClinicObj ;
		LocalDate datel = null;
		masterClinicObj = masterClinicRepoObj.findByDate(datel.now());
		System.out.println(masterClinicObj);
		return masterClinicObj;
	}
	
	public List<masterClinic> currentSeq(LocalDate date) {
		
		List<masterClinic> masterClinicObj ;
		LocalDate datel = null;
		masterClinicObj =	masterClinicRepoObj.findTopByDateAndIsdoneGreaterThanOrderByAppointmentseqDesc(datel.now(), 0);
		
		System.out.println(masterClinicObj);
		return masterClinicObj;
	}
	
	public masterClinic findById(Long id) {
		masterClinic masterClinicobj= new masterClinic() ;
		return masterClinicobj = masterClinicRepoObj.findById(id).orElse(masterClinicobj);
		
	}
	
	public void updateIsDoneStatus(Long id,int isdone) {
		//updateIsDoneStatus(id, isdone);
		masterClinic masterClinicobj= new masterClinic();
		masterClinicobj = masterClinicRepoObj.findById(id).orElse(masterClinicobj);
		masterClinicobj.setIsdone(isdone);
		masterClinicRepoObj.save(masterClinicobj);
	}
	
	public masterClinic save(masterClinic masterClinicobj) {
		return masterClinicRepoObj.save(masterClinicobj);
	}
	public guestDetails saveGuest(guestDetails guestDetailsObj) {
		
		return guestDetailsRepoObj.save(guestDetailsObj);
	}
	
	
	public masterPatient savePatient(masterPatient patient) {
		
		masterPatientRepoObj.save(patient);
		
		return patient;
	}
	
	@Override
	public List<masterClinic> findByDateOrderByAppointmentseq(LocalDate date) {
		List<masterClinic> masterClinicObj ;
		//LocalDate datel = null;
//		masterClinicObj= masterClinicRepoObj.findByDateOrderByAppointmentseqDesc(datel.now());
		masterClinicObj= masterClinicRepoObj.findByDateOrderByAppointmentseqDesc(date);
		return masterClinicObj;
		
	}
	
	

}
