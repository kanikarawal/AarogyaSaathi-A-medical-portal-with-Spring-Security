package com.aarogyasathi.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aarogyasathi.entity.Doctor;
import com.aarogyasathi.entity.Patient;
import com.aarogyasathi.exception.DoctorServiceException;
import com.aarogyasathi.exception.PatientServiceException;
import com.aarogyasathi.repository.DoctorRepository;
import com.aarogyasathi.repository.PatientRepository;

@Service
public class DoctorService {

	@Autowired
	private DoctorRepository doctorRepo;
	
//	@Autowired
//	private PatientRepository patientRepo;
//	
//	public int addDoctor(Doctor doctor) throws DoctorServiceException{
//		Optional<Doctor> checkDoctor=doctorRepo.findByEmail(doctor.getEmail());
//		Optional<Patient> checkPatient=patientRepo.findByEmail(doctor.getEmail());
//		
//		if(checkDoctor.isEmpty() && checkPatient.isEmpty()) {
//			Doctor savedDoctor=doctorRepo.save(doctor);
//			return doctor.getDoctorId();
//		}
//		else
//		{
//			throw new DoctorServiceException("This email ID is already registered");
//		}
//		
//	}
	
	public Optional<Doctor> getDoctorById(int id) {
        return doctorRepo.findById(id);
    }
	
	public Optional<Doctor> getDoctorByEmail(String email) {
        return doctorRepo.findByEmail(email);
    }
	
	public Doctor login(String email, String password) throws DoctorServiceException{
		Optional<Doctor> doctor = doctorRepo.findByEmailAndPassword(email, password);
		if(doctor.isPresent())
			return doctor.get();
		else
			throw new DoctorServiceException("Invalid Email/Password");
	} 
	
	public List<Doctor> getAllDoctors(){
		 List<Doctor> allDoctors=doctorRepo.findAll();
		 return  allDoctors;
	}

	
	
}

