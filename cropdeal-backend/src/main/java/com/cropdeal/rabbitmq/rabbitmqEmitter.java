package com.cropdeal.rabbitmq;

import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class rabbitmqEmitter {
	
	
	@Autowired
	private RabbitTemplate rabbitTemplate;	
	
	
	
	public String emmitmsg(Map<String, String > emmiterap) {
		
		rabbitTemplate.convertAndSend("profile", emmiterap);
		
		return "success";
		
	}
	

}
