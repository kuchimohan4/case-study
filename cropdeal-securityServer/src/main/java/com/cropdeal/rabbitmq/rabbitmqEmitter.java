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
		
		rabbitTemplate.convertAndSend("security", emmiterap);
		
		return "success";
		
	}
	
//	public String emmitmsgtoother(Map<String, String > emmiterap) {
//		
//		rabbitTemplate.convertAndSend(emmiterap.get("queue"), emmiterap);
//		
//		return "success";
//		
//	}
	

}
