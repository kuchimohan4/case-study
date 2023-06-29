package com.cropdeal.rabbitmq;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropdeal.entites.BankAccounts;
import com.cropdeal.entites.address;
import com.cropdeal.entites.profile;
import com.cropdeal.exception.noProfileFoundException;
import com.cropdeal.mail.mailsenderservice;
import com.cropdeal.service.profileService;

@Service
public class rabbitmqConsumer {
	
	private static Logger logger=LoggerFactory.getLogger(rabbitmqConsumer.class);
	
	@Autowired
	private mailsenderservice mailsenderservice;
	
	@Autowired
	private profileService profileService;
	@Autowired
	private rabbitmqEmitter rabbitmqEmitter;

	@RabbitListener(queues = "profile")
	public void getRabbitmqMsg(Map<String, String> consumemap) throws NumberFormatException, noProfileFoundException {
		try {
		System.out.println(consumemap.entrySet());
		if(consumemap.get("type").equals("addedProfile")) {
		mailsenderservice.sendProfileAddedMail(consumemap.get("email"),consumemap.get("name"));
		}else if(consumemap.get("type").equals("updatedProfile")) {
			mailsenderservice.sendProfileUpdatedMail(consumemap.get("email"),consumemap.get("name"));
		}else if (consumemap.get("type").equals("BankAccountUpdated")) {
			mailsenderservice.sendBankAccountUpdatedMail(consumemap.get("email"),consumemap.get("name"), new BankAccounts(0, consumemap.get("accountHolderName"), consumemap.get("accountNumber"), consumemap.get("ifscCode")));
			
		}else if (consumemap.get("type").equals("addressChanged")) {
			mailsenderservice.sendAddressUpdatedMail(consumemap.get("email"),consumemap.get("name"), new address(0, consumemap.get("addressLine1"), consumemap.get("addressLine2"), consumemap.get("city"), consumemap.get("state"), Integer.parseInt(consumemap.get("pinCode"))));
			
		}else if (consumemap.get("type").equals("needMail")) {
			
			profile profile=profileService.getprofileById(Integer.parseInt(consumemap.get("userId")) );
			
			consumemap.put("email", profile.getEmailId());
			consumemap.put("name", profile.getName());
			rabbitmqEmitter.emmitmsgtoother(consumemap);
		}
		}catch (Exception e) {
			logger.error("failed to send mail because no profile found this user");
		}
		
	}
	
}
