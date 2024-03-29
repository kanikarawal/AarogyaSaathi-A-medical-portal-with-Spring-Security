package com.aarogyasathi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aarogyasathi.entity.Admin;
import com.aarogyasathi.entity.AuthenticationResponse;
import com.aarogyasathi.entity.Doctor;
import com.aarogyasathi.entity.Patient;
import com.aarogyasathi.exception.AdminServiceException;
import com.aarogyasathi.exception.PatientServiceException;
import com.aarogyasathi.service.AdminService;
import com.aarogyasathi.service.AuthenticationService;
import com.aarogysathi.dto.LoginDetails;
import com.aarogysathi.dto.LoginStatus;

@RestController
@CrossOrigin
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AuthenticationService authService;
	
	@PostMapping("/adminsignup")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody Admin admin){
		return ResponseEntity.ok(authService.registerAdmin(admin));
	}
	
//	 @PostMapping("/admin/login")
//		public LoginStatus login(@RequestBody LoginDetails loginDetails) {
//			try {
//				Admin admin = adminService.adminLogin(loginDetails.getEmail(), loginDetails.getPassword());
//				LoginStatus status = new LoginStatus();
//				status.setStatus(true);
//				status.setMessage("Login successful!");
//				status.setId(admin.getAdminId());
//				return status;
//			}
//			catch (AdminServiceException e) {
//				LoginStatus status = new LoginStatus();
//				status.setStatus(false);
//				status.setMessage(e.getMessage());
//				return status;
//			}
//		}
}
