package com.cropdeal.rabbitmq;

import java.util.Map;

import jakarta.mail.MessagingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cropdeal.mail.mailsenderservice;


@Service
public class rabbitmqConsumer {
	
	@Autowired
	private mailsenderservice mailsenderservice;
	
	
	@Autowired
	private rabbitmqEmitter rabbitmqEmitter;

	@RabbitListener(queues = "security")
	public void getRabbitmqMsg(Map<String, String> consumemap) throws NumberFormatException, MessagingException {
		
		System.out.println(consumemap.entrySet());
		if(consumemap.get("type").equals("AccountRegistration")) {	
		mailsenderservice.sendotpForregistration(consumemap.get("email"), consumemap.get("name"),consumemap.get("otp"));
		}else if (consumemap.get("type").equals("ValidatedMail")) {
			
			mailsenderservice.sendregistrationSuccessMail(consumemap.get("email"), consumemap.get("name"));
		}
		
	}
	
}
