package com.clinic.appointment.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.clinic.appointment.model.guestDetails;
import com.clinic.appointment.model.masterClinic;
import com.clinic.appointment.model.masterPatient;
import com.clinic.appointment.model.patientPrescription;
import com.clinic.appointment.service.masterClinicServiceImpl;
import com.clinic.appointment.service.patientPrescriptionService;

import ch.qos.logback.core.util.FileSize;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class WebController {

	private static final String EXTERNAL_FILE_PATH = "C:\\Users\\Vishal\\Downloads\\";
	@Autowired
	masterClinicServiceImpl masterClinicService;
	
	@Autowired
	patientPrescriptionService patientPrescriptionService;
	
	@Autowired
	com.clinic.appointment.service.masterPatientService masterPatientService;
	
	@Autowired  
	private HttpServletRequest request;
	
	
	@RequestMapping(value="/allappointments", method=RequestMethod.GET)
    public String allappointments(Model model) {
		
		 //model.addAttribute("todaysAllDataSize", 0);
		
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	    String email = loggedInUser.getName(); 
	    System.out.println(email);
		
		 LocalDate datel = null;
		 datel = datel.now();
		 List<masterClinic> masterClinic=masterClinicService.findByDate(datel);
	        model.addAttribute("appointments", masterClinic);
	        model.addAttribute("todaysAllDataSize", masterClinic.size());
		return "appointmentList";
		
	}
	@RequestMapping(value="/register", method=RequestMethod.GET)
    public String register(Model model) {
		
		 //model.addAttribute("todaysAllDataSize", 0);
		 
		return "register";
		
	}
	
	
//	@ExceptionHandler

	
	@RequestMapping(value="/userlogin", method=RequestMethod.GET)
    public String login(Model model) {
		System.out.println("here");
		 //model.addAttribute("todaysAllDataSize", 0);
		 
		return "login";
		
	}
	
	@ResponseBody
	@PostMapping(value="/verifyEmail")
	public Map<String, Object> verifyEmail(@RequestParam(required = false) String id,@RequestParam String patient_id,@RequestParam(required = false) MultipartFile file) {
		
		masterPatientService.verifyEmail();
		Map<String, Object> map = new HashMap<>();
    	map.put("success", "1");
    	return map;
		
	}
	
	@RequestMapping(value="/verifyOtp/{patient_id}", method=RequestMethod.GET)
		public String verifyOtpScreen(@PathVariable(required = false) Long patient_id,Model model) {
		if(patient_id!=null)
		 model.addAttribute("patient_id", patient_id);
		
		return "verifyotp";
	}
	
	@RequestMapping(value="/verifyOtp", method=RequestMethod.GET)
	public String verifyOtpScreen(HttpServletRequest request, Model model) {
		
		if(request.getSession().getAttribute("patient_id")!=null) {
			model.addAttribute("patient_id", request.getSession().getAttribute("patient_id"));
			
			//Callable<String> as return type
			System.out.println("request.isAsyncSupported() "+request.isAsyncSupported()); 	
			
		return "verifyotp";
		}
		else {
			return "redirect:/login";
		}
	}
	@ResponseBody
	@PostMapping(value="/verifyOtp")
	public Map verifyOtp(@RequestParam String patient_id,@RequestParam String otp) {
		
		Map<String, Object> map = new HashMap<>();
		if(patient_id.equals("") || otp.equals("")) {
			map.put("error", "Patient id/ Otp is not provided");
			map.put("errorcode", "PN");
			map.put("res", "false");
		}
		else {
			map=masterPatientService.verifyOtp(patient_id, otp);
			
		}
		
    	return map;
		
	}
	
	@ResponseBody
	@PostMapping(value="/savePatient")
	public Map savePatient(masterPatient patient) {
		System.out.println(patient);
		Map<String, Object> map = new HashMap<>();
    	
    	
		
		if(masterPatientService.isEmailRegistered(patient.getEmail().trim())) {
			map.put("success", "0");
			map.put("error","Email already registered");
			return map;
		}
		
		LocalDate datel = null;
		 datel = datel.now();
		patient.setDate(datel);
		patient.setStatus(1);//default active
		patient.setIsVerified(0); //default false
		map.put("patient", patient.toString());
		String otpP =String.format("%08d", new SecureRandom().nextInt(10_000_000));
		System.out.println(otpP);
		patient.setOtp(otpP);
		
		patient.setOtpverifyattempt(0);
		patient=masterClinicService.savePatient(patient);
		masterPatientService.sendOtpEmail(patient.getEmail(),patient.getOtp(),String.valueOf(patient.getPatient_id()));
//		return patient.toString();
		map.put("success", "1");
		
		return map;
	}
	
	@PostMapping("/updateremarks")
	public String updateremarks(@RequestParam Long id,@RequestParam String remarks,Model model) {
		
		//System.out.println(remarks);
		//System.out.println(id);
		masterClinic masterClinicObj = new masterClinic();
		masterClinicObj = masterClinicService.findById(id);
		masterClinicObj.setRemarks(remarks);
		masterClinicService.save(masterClinicObj);
		
		return "appointmentList";
	}
	
	@RequestMapping(value="/update/{id}/{isdone}", method=RequestMethod.POST)
	@ResponseBody
	//public String updateIsDone(@PathVariable Long id,@PathVariable int isdone,Model model) {
		public Map updateIsDone(@PathVariable Long id,@PathVariable int isdone,Model model) {
				masterClinicService.updateIsDoneStatus(id, isdone);
		
			/* return "appointmentList"; */
				
				Map<String, Object> map = new HashMap<>();
	        	map.put("success", "1");
	        	return map;
				
	        //return "redirect:/allappointments";
	}
	
		@RequestMapping(value="/", method=RequestMethod.GET)
	    public String home(Model model) {
			
			
			System.out.println("Thread Name " +Thread.currentThread().getName());
			
		 System.out.println("In WEB MyController!!!");
	        model.addAttribute("message", "hi");
	       // List<Details> details=detService.getAll();
	        //model.addAttribute("details", details);
	        List<masterClinic> allData = masterClinicService.getDetails();
//	        System.out.println(allData.get(0));
	        masterClinic masterClinicObj = new masterClinic();
	        Date dateobj = new Date();
	        List<masterClinic> allDataDatewise ;
	        LocalDate datel = null;
	        allDataDatewise = masterClinicService.findByDate(datel.now());
	        System.out.println("allData todays "+allDataDatewise.size());
	        model.addAttribute("allDataSize", allData.size());
	        model.addAttribute("todaysAllDataSize", allDataDatewise.size());
	        
	        masterClinicObj.setId(Long.valueOf("1"));
	        System.out.println(masterClinicService.findById(masterClinicObj.getId()));
	        RestTemplate temp = new RestTemplate();
	        
	        //get current seq having status >0 , done /prescription uploaded
	        allData = masterClinicService.currentSeq(datel.now());
	        if(allData.size()>0) {
	        	
	        	model.addAttribute("currentSeq", allData.get(0).getAppointmentseq());
	        }
	        else {

	        	model.addAttribute("currentSeq", 0);
	        }
	        
	        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
	        
//	        if (!(authentication instanceof AnonymousAuthenticationToken)) {}
	        
//	        System.out.println(loggedInUser.getName());
	        //if(!loggedInUser.getName().contains("anonymousUser")) {
	        	model.addAttribute("loggedInUser", loggedInUser);
	        //}
//	        model.addAttribute("User", loggedInUser.get);
//	        List<Details> details;
//	        details = temp.getForObject("http://localhost:8080/hello/getAll", List.class);
//	        System.out.println(details);
//	        model.addAttribute("details", details);
//	        return "index";
	        return "appointment";
	}
		
		@RequestMapping(value="/upload", method=RequestMethod.POST)
		  @ResponseBody
		  public Map<String, Object> upload(@RequestParam String id,@RequestParam String patient_id,@RequestParam MultipartFile file) {
				
			System.out.println(id);
			System.out.println(file.getOriginalFilename());
			
			
			masterClinicService.updateIsDoneStatus(Long.valueOf(id), 2); //2 is done and prescription uploaded
			
			
			
			patientPrescription patientPrescription= new patientPrescription();
			patientPrescription.setPatientId(Long.valueOf(patient_id));
			patientPrescription.setPrescriptionId(Long.valueOf(id));
			
			try {
				patientPrescription.setPatientPrescription(file.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 LocalDate datel = null;
			 datel = datel.now();
			patientPrescription.setDateAdded(datel);
			patientPrescriptionService.savePrescription(patientPrescription);
			
			File f = new File("D:\\Projects\\Clinic_app\\files\\"+patient_id+"_"+id+"_"+datel+file.getOriginalFilename());
			try {
				file.transferTo(f);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			Map<String, Object> map = new HashMap<>();
        	map.put("success", "1");
        	return map;
		}
		
		
		@RequestMapping(value="/viewItem", method=RequestMethod.POST)
		  @ResponseBody
		  public Map<String, Object> viewItem(@RequestParam String id,@RequestParam String patient_id) {
				
			System.out.println(id);
			//System.out.println(file.getOriginalFilename());
			
			
					
			
			patientPrescription patientPrescription= new patientPrescription();
			patientPrescription.setPatientId(Long.valueOf(patient_id));
			patientPrescription.setPrescriptionId(Long.valueOf(id));
			
			/*
			 * try { patientPrescription.setPatientPrescription(file.getBytes()); } catch
			 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
			 */
			 LocalDate datel = null;
			 datel = datel.now();
			patientPrescription.setDateAdded(datel); 
			
			patientPrescription = patientPrescriptionService.getPrescription(patientPrescription);
			
			//System.out.println(patientPrescription);
			
			Map<String, Object> map = new HashMap<>();
	      	
			
			map.put("imgData", Base64.getEncoder().encodeToString(patientPrescription.getPatientPrescription()));
			map.put("success", "1");
			//File f = new File("D:\\Projects\\Clinic_app\\files\\"+patient_id+"_"+id+"_"+datel+file.getOriginalFilename());
			/*
			 * try { file.transferTo(f); } catch (IllegalStateException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) { //
			 * TODO Auto-generated catch block e.printStackTrace(); }
			 */
			
			
			
      	return map;
		}
		
		@RequestMapping(value="/deleteItem", method=RequestMethod.POST)
		  @ResponseBody
		  public Map<String, Object> deleteItem(@RequestParam String id,@RequestParam String patient_id) {
				
			System.out.println(id);
			//System.out.println(file.getOriginalFilename());
			
			Map<String, Object> map = new HashMap<>();
	      	
					
			
			patientPrescription patientPrescription= new patientPrescription();
			patientPrescription.setPatientId(Long.valueOf(patient_id));
			patientPrescription.setPrescriptionId(Long.valueOf(id));
			
			/*
			 * try { patientPrescription.setPatientPrescription(file.getBytes()); } catch
			 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
			 */
			 LocalDate datel = null;
			 datel = datel.now();
			patientPrescription.setDateAdded(datel); 
			try {
			 patientPrescriptionService.deleteByPrescriptionId(patientPrescription);
			}
			catch(Exception e) {
				 map.put("deleted", "0");
			}
			 try {
			 	masterClinic masterClinicObj = new masterClinic();
				masterClinicObj = masterClinicService.findById(patientPrescription.getPrescriptionId());
				masterClinicObj.setIsdone(1);
				masterClinicService.save(masterClinicObj);
			 }
			 catch(Exception e) {
				 map.put("statusChanged", "0");
			 }
			//System.out.println(patientPrescription);
			
			
			
			map.put("success", "1");
			//File f = new File("D:\\Projects\\Clinic_app\\files\\"+patient_id+"_"+id+"_"+datel+file.getOriginalFilename());
			/*
			 * try { file.transferTo(f); } catch (IllegalStateException e) { // TODO
			 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) { //
			 * TODO Auto-generated catch block e.printStackTrace(); }
			 */
			
			
			
    	return map;
		}
		
		@RequestMapping(value="/getCurrentSeq", method=RequestMethod.POST)
		  @ResponseBody
			public Map<String, Object> getCurrentSeq() {
			
			LocalDate datel = null;
			 datel = datel.now();
			List<masterClinic> masterClinicListObj ;
			 masterClinicListObj = masterClinicService.currentSeq(datel);
			 
			
			Map<String, Object> map = new HashMap<>();
        	map.put("success", "1");
        	map.put("appointmentSeq", masterClinicListObj.get(0).getAppointmentseq());
        	
        	return map;
		}
		
//	@PostMapping(value="/bookAppointment") 
	@RequestMapping(value="/bookAppointment", method=RequestMethod.POST)
	  @ResponseBody
//	public String userUpdate(@RequestParam String name,@RequestParam String email,@RequestParam String phone, Model model) {
		public Map<String, Object> userUpdate(@RequestParam String name,@RequestParam String email,@RequestParam String phone, Model model) {
			
		System.out.println(name);
		System.out.println(email);
		System.out.println(phone);
		
		 masterClinic masterClinicObj = new masterClinic();
		 masterClinicObj.setPatient_name(name);
		 masterClinicObj.setIsdone(0);
		 LocalDate datel = null;
		 datel = datel.now();
		 masterClinicObj.setDate(datel);
		// masterClinicObj= masterClinicService.save(masterClinicObj);
		 Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		 
		 
		 
		 List<masterClinic> masterClinicListObj ;
		 masterClinicListObj = masterClinicService.findByDateOrderByAppointmentseq(datel);
		 
		 System.out.println(masterClinicListObj);
		 if(masterClinicListObj.size()==0) {
			 masterClinicObj.setAppointmentseq(1);
			 model.addAttribute("todaysAllDataSize",1);
		 }
		 else {
			 System.out.println(masterClinicListObj.get(0).getAppointmentseq());
			 masterClinicObj.setAppointmentseq(masterClinicListObj.get(0).getAppointmentseq()+1);
			 model.addAttribute("todaysAllDataSize", masterClinicListObj.get(0).getAppointmentseq()+1);
		 }
		 
		 
		 if(request.getSession().getAttribute("patient_id") !=null) {
			 
			 masterClinicObj.setPatient_id((Long) request.getSession().getAttribute("patient_id"));
		 }
		 
		 masterClinicObj= masterClinicService.save(masterClinicObj);
		 
		 
		 System.out.println(masterClinicObj.toString());
		 
		 if(!email.equals("") || !phone.equals("")) {
		 guestDetails guestDetailsobj = new guestDetails();
		 guestDetailsobj.setMasterId(masterClinicObj.getId());
		 guestDetailsobj.setEmail(email);
		 guestDetailsobj.setMobile(phone);
		 guestDetailsobj.setDate(datel);
		 masterClinicService.saveGuest(guestDetailsobj);
		 }
		 
		    
//	        if (!(authentication instanceof AnonymousAuthenticationToken)) {}
	        
//	        System.out.println(loggedInUser.getName());
	        //if(!loggedInUser.getName().contains("anonymousUser")) {
	        	model.addAttribute("loggedInUser", loggedInUser);
		 
		//return "appointment";
	        	
	        	Map<String, Object> map = new HashMap<>();
	        	map.put("success", "1");
	        	map.put("appointmentSeq", masterClinicObj.getAppointmentseq());
	        	
	        	return map;
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public String editUser(@PathVariable Long id,Model model) {
//		detService.getDetailsById(id);
		RestTemplate temp = new RestTemplate();
//		Details detail;
//		detail = temp.getForObject("http://localhost:8080/hello/get/"+id, Details.class);
//		model.addAttribute("details", detail);
		return "editUser";
	}
	
/*	@PostMapping("/userUpdate")
	public String userUpdate(Details details,Model model) {
	   System.out.println(details.getName());
	   System.out.println(details);
	   RestTemplate temp = new RestTemplate();
	   temp.postForEntity("http://localhost:8080/userUpdate", details, Details.class);
	   model.addAttribute("updatedMessage", "Record updated successfully for "+details.getName());
	   
       List<Details> detailsAll;
       detailsAll = temp.getForObject("http://localhost:8080/hello/getAll", List.class);
       model.addAttribute("details", details);
	    //return "redirect:/";
//       forward:/
	   return "index";
	}
	*/
	@GetMapping(value = "/download")
    public ResponseEntity<Resource> downloadFilePdf(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileName="1688488-ClaimForm (1).pdf";
		fileName="5_tariff.jpg";
		File file = new File(EXTERNAL_FILE_PATH + fileName);
		if (file.exists()) {

			//get the mimetype
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				//unknown mimetype so set the mimetype to application/octet-stream
				mimeType = "application/octet-stream";
			}
			HttpHeaders headers = new HttpHeaders();
	        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
	        headers.add("Pragma", "no-cache");
	        headers.add("Expires", "0");
			response.setContentType(mimeType);
			
			/**
			 * Here we have mentioned it to show inline
			 */
			//response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

			 //Here we have mentioned it to show as attachment
			 response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

			response.setContentLength((int) file.length());

			//InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			
			InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

			return ResponseEntity.ok()
		            .headers(headers)
		            .contentLength(file.length())
		            .contentType(MediaType.parseMediaType(mimeType))
		            .body(resource);
			//FileCopyUtils.copy(inputStream, response.getOutputStream());

    }
		return null;
	
}
}
