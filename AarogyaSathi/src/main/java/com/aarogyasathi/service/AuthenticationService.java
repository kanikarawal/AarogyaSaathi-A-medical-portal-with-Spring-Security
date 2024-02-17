package com.aarogyasathi.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aarogyasathi.entity.Admin;
import com.aarogyasathi.entity.AuthenticationResponse;
import com.aarogyasathi.entity.Doctor;
import com.aarogyasathi.entity.Patient;
import com.aarogyasathi.repository.AdminRepository;
import com.aarogyasathi.repository.DoctorRepository;
import com.aarogyasathi.repository.PatientRepository;

@Service
public class AuthenticationService {
	private final PatientRepository patientRepo;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authManager;
	private final DoctorRepository doctorRepo;
	private final AdminRepository adminRepo;

	public AuthenticationService(PatientRepository patientRepo, PasswordEncoder passwordEncoder, JwtService jwtService,
			AuthenticationManager authManager, DoctorRepository doctorRepo, AdminRepository adminRepo) {
		super();
		this.patientRepo = patientRepo;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
		this.authManager = authManager;
		this.doctorRepo = doctorRepo;
		this.adminRepo = adminRepo;
	}

	public AuthenticationResponse register(Patient request) {
		Patient patient = new Patient();
		patient.setName(request.getName());
		patient.setEmail(request.getEmail());
		patient.setCity(request.getCity());
		patient.setDateOfBirth(request.getDateOfBirth());
		patient.setGender(request.getGender());
		patient.setMobileNo(request.getMobileNo());
		patient.setPassword(passwordEncoder.encode(request.getPassword()));
		patient.setRole("PATIENT");
		patient=patientRepo.save(patient);
		
		String token= jwtService.generateToken(patient.getEmail());
		
		return new AuthenticationResponse(token);
		
	}
	
	public AuthenticationResponse registerDoctor(Doctor request) {
		Doctor doctor=new Doctor();
		doctor.setDoctorName(request.getDoctorName());
		doctor.setEmail(request.getEmail());
		doctor.setMobileNo(request.getMobileNo());
		doctor.setPassword(passwordEncoder.encode(request.getPassword()));
		doctor.setQualification(request.getQualification());
		doctor.setSpecialization(request.getSpecialization());
		doctor.setRole("DOCTOR");
		doctor=doctorRepo.save(doctor);
		
		String token= jwtService.generateToken(doctor.getEmail());
		
		return new AuthenticationResponse(token);
		
	}
	
	public AuthenticationResponse registerAdmin(Admin request) {
		Admin admin=new Admin();
		admin.setEmail(request.getEmail());
		admin.setPassword(passwordEncoder.encode(request.getPassword()));
		admin=adminRepo.save(admin);
		
		String token=jwtService.generateToken(admin.getEmail());
		
		return new AuthenticationResponse(token);
		
	}
	
	
	
	public ResponseEntity<?> authenticate(String email, String password) {
		
		try{
			
			authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			Doctor doctor=doctorRepo.findByEmail(email).orElse(null);
			
			Patient patient=patientRepo.findByEmail(email).orElse(null);
			Admin admin=adminRepo.findByEmail(email).orElse(null);
			
			 if (patient != null) {
		            String token = jwtService.generateToken(patient.getEmail());
		           return new ResponseEntity<>(new AuthenticationResponse(token),HttpStatus.OK);
		        } 
			 else if(doctor != null) {
		            String token = jwtService.generateToken(doctor.getEmail());
		            return new ResponseEntity<>(new AuthenticationResponse(token),HttpStatus.OK);
		        } 
			 else {
				 String token = jwtService.generateToken(admin.getEmail());
		            return new ResponseEntity<>(new AuthenticationResponse(token),HttpStatus.OK);
			 }
			 
			 

		}
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or password is not valid");
		}
		
	}	
}
