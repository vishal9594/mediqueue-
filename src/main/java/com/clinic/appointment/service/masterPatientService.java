package com.clinic.appointment.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.clinic.appointment.config.MyConfig;
import com.clinic.appointment.model.masterPatient;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class masterPatientService implements UserDetailsService {
	
	@Autowired
	private masterPatientRepo masterPatientRepo;
	
	@Autowired
	private JavaMailSender JavaMailSender;
	
	@Autowired  
	private HttpServletRequest request;
	
	@Value("${appUrl}")
	private String appUrl;
	
	@Autowired
    private MyConfig config;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//username is email here
		Optional<masterPatient> user =masterPatientRepo.findByEmail(username);
		if(user.isPresent()) {
			System.out.println("found "+username);
			var userObj = user.get();
			System.out.println(userObj);
			request.getSession().setAttribute("patient_id", userObj.getPatient_id());
			return User.builder().username(userObj.getEmail()).password("{noop}"+userObj.getPassword()).build();
		}
		else {
			System.out.println("not found "+username);
		}
		return null;
	}
	
	public Boolean isEmailRegistered(String email) {
		Optional<masterPatient> user =masterPatientRepo.findByEmail(email);
		if(user.isPresent()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public Boolean updateVerified(masterPatient masterPatient) {
		masterPatient.setIsVerified(1);
//		masterPatient.setOtp("");
		masterPatientRepo.save(masterPatient);
		return true;
		
	}
	public Map verifyOtp(String patient_id,String Otp) {
		Optional<masterPatient> masterPatient= masterPatientRepo.findById(Long.valueOf(patient_id));
		
		Map<String, Object> map = new HashMap<>();
		
		
		if(masterPatient.isPresent()) {
			
			if(masterPatient.get().getOtpverifyattempt()==null) {
				masterPatient.get().setOtpverifyattempt(0);
			}
			
			if(masterPatient.get().getIsVerified()==1) {
				map.put("error", "User already verified");
				map.put("errorcode", "U1");
				map.put("res", "false");
				return map;
			}
			
			if(masterPatient.get().getOtpverifyattempt()>=3) {
				map.put("error", "Max number of otp verification exceeded, kindly regenerate OTP");
				map.put("errorcode", "O3");
				map.put("res", "false");
				return map;
			}
			else {
				
				
				masterPatient.get().setOtpverifyattempt(masterPatient.get().getOtpverifyattempt()+1);
				
				//System.out.println(masterPatient.get().getOtp());
				if(masterPatient.get().getOtp().equals(Otp)) {
					updateVerified(masterPatient.get());
					map.put("res", "true");
					return map;
				}
				else {
					masterPatientRepo.save(masterPatient.get());
					map.put("error", "Otp not matched");
					map.put("res", "false");
					map.put("errorcode", "ON");
					return map;
				}
			}
			
			
			
			
		}
		else {
			map.put("res", "false");
			return map;
		}
	}
	
	public void verifyEmail() {
		/*SimpleMailMessage mail= new SimpleMailMessage();
		mail.setText("Click to verify your account by providing OTP recived here. <a>link</a>");
		mail.setTo("vishalsarde97@gmail.com");*/
		
		MimeMessage mail =JavaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail);
		try {
			
			helper.setText("Click to verify your account by providing OTP recived here. <a href="+config.getServerUrl()+"/verify>link</a>", true);
			//helper.setText("Click to verify your account by providing OTP recived here. <a href="+appUrl+"/verify>link</a>", true);
			
			helper.setTo("vishalsarde97@gmail.com");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JavaMailSender.send(mail);
	}

	public void sendOtpEmail(String email,String Otp,String patient_id) {
		/*SimpleMailMessage mail= new SimpleMailMessage();
		mail.setText("Click to verify your account by providing OTP received here. <a>link</a>");
		mail.setTo("vishalsarde97@gmail.com");*/
		
		MimeMessage mail =JavaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail);
		try {
			//helper.setText("Click to verify your account by providing OTP received here. <a href="+appUrl+"/verifyOtp/"+patient_id+">link </a></br>"
			//		+ " OTP: "+Otp, true);
			
			
			helper.setText("Click to verify your account by providing OTP received here. <a href="+config.getServerUrl()+"/verifyOtp/"+patient_id+">link </a></br>"
					+ " OTP: "+Otp, true);
			
			helper.setTo(email);
			helper.setSubject("Account verification - Mediqueue+");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JavaMailSender.send(mail);
	}
}
