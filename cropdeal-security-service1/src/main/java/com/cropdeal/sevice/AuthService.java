package com.cropdeal.sevice;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.ThrowableCauseExtractor;
import org.springframework.stereotype.Service;

import com.cropdeal.entity.otpManager;
import com.cropdeal.entity.userCredentials;
import com.cropdeal.exception.EmailAlreadyExistsException;
import com.cropdeal.exception.InvalidOtpException;
import com.cropdeal.rabbitmq.rabbitmqEmitter;
import com.cropdeal.repository.otpMangerRepositry;
import com.cropdeal.repository.userCreantialsRepositry;

@Service
public class AuthService {
	
	@Autowired
	private userCreantialsRepositry userCreantialsRepositry;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private jwtService jwtService;
	
	@Autowired
	private otpMangerRepositry otpMangerRepositry;
	
	@Autowired
	private rabbitmqEmitter rabbitmqEmitter;
	
	public String saveUser(userCredentials userCredentials) {
		
		userCreantialsRepositry.findByEmail(userCredentials.getEmail()).ifPresent((obj)->{ throw new EmailAlreadyExistsException("Email alredy exists please login"); });
		userCredentials.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
		userCredentials.setEnabled(false);
		userCredentials.setAccountNonLocked(true);
		userCreantialsRepositry.save(userCredentials);
		System.out.println(userCredentials.getRole());
		
		//otp genration
		Random random=new Random();
		int otp=random.nextInt(100000, 999999);
		otpManager otpManager=new otpManager(otp,userCredentials.getEmail(),"AccountRegistration",LocalDateTime.now(),LocalDateTime.now().plusMinutes(5));
		otpMangerRepositry.save(otpManager);
		
//		mail
		Map<String, String> mailmap=new HashMap<>();
		mailmap.put("email", userCredentials.getEmail());
		mailmap.put("name", userCredentials.getName());
		mailmap.put("type", "AccountRegistration");
		mailmap.put("otp", ""+otp);
		
		rabbitmqEmitter.emmitmsg(mailmap);
		
		
		return "otp sent to mail for validation";
		
	}
	
	public String validateMail(Map<String, String> otp) throws InvalidOtpException {
		
		String mail=otp.get("email");
		int otpentered=Integer.parseInt(otp.get("otp"));
		userCredentials userCredentials=   userCreantialsRepositry.findByEmail(mail).orElseThrow(()-> new InvalidOtpException("invalid email id"));
		if(userCredentials.getEnabled()) {
			throw new InvalidOtpException("eMail already verified");
		}
		
		otpManager otpManager=otpMangerRepositry.findByGenratedForAndOtpAndGenratedForReason(mail, otpentered, "AccountRegistration").orElseThrow(()->new InvalidOtpException("Invalid Otp"));
		if(otpManager.getExpiresAt().isBefore(LocalDateTime.now())) {
			throw new InvalidOtpException("otp Expired");
			
		}
		
		
		userCredentials.setEnabled(true);
		userCreantialsRepositry.save(userCredentials);
		
//		mail
		Map<String, String> mailmap=new HashMap<>();
		mailmap.put("email", userCredentials.getEmail());
		mailmap.put("name", userCredentials.getName());
		mailmap.put("type", "ValidatedMail");
		
		rabbitmqEmitter.emmitmsg(mailmap);
//		otpMangerRepositry.deleteByGenratedFor(mail);
		
		return "user added sucessfully";
		
		
	}
	
	
	public String genarateToken(int userId,Collection<? extends GrantedAuthority> authorities) {
		
		
		
//		System.out.println("hello1"+role);
		return jwtService.genarateToken(userId,authorities);
		
		
	}
	
	public void validateToken(String token) {
		
		jwtService.validateToken(token);
	}


	public int retreveId(String mail) {
		
		Optional<userCredentials> usercred=userCreantialsRepositry.findByEmail(mail);
		userCredentials userCredential=usercred.orElse(null);
		
		return userCredential.getId();
	}
	
	

}
