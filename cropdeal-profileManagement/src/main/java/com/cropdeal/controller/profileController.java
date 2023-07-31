package com.cropdeal.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.fileupload.portlet.PortletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cropdeal.entites.BankAccounts;
import com.cropdeal.entites.address;
import com.cropdeal.entites.profile;
import com.cropdeal.exception.noProfileFoundException;
import com.cropdeal.service.profileService;

import io.jsonwebtoken.io.IOException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController 
@RequestMapping("/profile")
public class profileController {
	
	
	@Autowired
	private profileService profileServic;
	
	@Autowired
	private com.cropdeal.service.imgservice imgservice;
	
	
	private static final Logger logger = LoggerFactory.getLogger(profileController.class);
	
	
	@PostMapping("/addProfile")
	public ResponseEntity<?> addprofile(@Valid @RequestBody profile profile,BindingResult bindingResult) throws noProfileFoundException {
		
//		userid
		int userid=requestUserId();
		if(bindingResult.hasErrors()) {
			Map<String, String> errormap=new HashMap<>();
			bindingResult.getFieldErrors().forEach(error -> errormap.put(error.getField(), error.getDefaultMessage()));
			logger.error("user with ID:"+userid+" has failed to profile");
			return new ResponseEntity<>(errormap, HttpStatus.BAD_REQUEST) ;
		}
		profileServic.addprofile(userid ,profile);
		logger.info("user with ID:"+userid+" has added profile");
		return new ResponseEntity<>(new HashMap<>() ,HttpStatus.OK);
		
	}
	
	@PutMapping("/updateProfile")
	public ResponseEntity<?> updateProfile(@Valid @RequestBody profile profile,BindingResult bindingResult) throws noProfileFoundException {
		int userid=requestUserId();
		if(bindingResult.hasErrors()) {
			Map<String, String> errormap=new HashMap<>();
			bindingResult.getFieldErrors().forEach(error -> errormap.put(error.getField(), error.getDefaultMessage()));
			
			return new ResponseEntity<>(errormap, HttpStatus.BAD_REQUEST) ;
		}
		profileServic.updateProfile(userid, profile);
		logger.info("user with ID:"+userid+" has updated profile");
		return new ResponseEntity<>( new HashMap<>(),HttpStatus.OK);
		
	}
	@GetMapping("/profile/{id}")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public profile getprofileById(@PathVariable int id) throws noProfileFoundException {
		
		
		
	return profileServic.getprofileById(id);	
	}
	
	@GetMapping("/getprofile")
	public profile getprofile() throws noProfileFoundException{
		
		int userid=requestUserId();
		return profileServic.getprofileById(userid);
		
	}
	
	
	@PutMapping("/updateBankAccount")
	public void updateBankAccount(@RequestBody BankAccounts bankAccount) throws noProfileFoundException {
		int userid=requestUserId();
		profileServic.updateBankAccount(userid, bankAccount);
		logger.info("user with ID:"+userid+" has updated bank details");
	}
	
	@PutMapping("/updateaddress")
	public void updateAddress(@RequestBody address address) throws noProfileFoundException {
		int userid=requestUserId();
		profileServic.updateaddress(userid, address);
		logger.info("user with ID:"+userid+" has updated address deytails ");
	}
	
	
	private int requestUserId() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId = authentication.getName();
		return Integer.parseInt(userId);
		
	}
	
	
	@PostMapping("/uploadimg")
	public ResponseEntity<?> uploadimg(@RequestParam("image") MultipartFile file) throws IOException, java.io.IOException{
		imgservice.uploadimg(file);
//		imgservice.uploadimg(file);
		return ResponseEntity.status(HttpStatus.OK).body(new HashMap<>());
		
	}
	
	
	@GetMapping("/downloadimg/{imgname}")
	public ResponseEntity<?> downloadimage(@PathVariable String imgname){
		
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imgservice.downloadimg(imgname));
		
	}
	
	

	@ExceptionHandler()
	public ResponseEntity<String> handleemailalredyexist(Exception ex){
		
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	
	
	

}
