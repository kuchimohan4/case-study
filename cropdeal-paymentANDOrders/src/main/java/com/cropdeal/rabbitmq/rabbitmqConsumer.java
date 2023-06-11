package com.cropdeal.rabbitmq;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropdeal.entites.orders;
import com.cropdeal.exception.noProductFoundException;
import com.cropdeal.mail.mailsenderservice;
import com.cropdeal.models.product;
import com.cropdeal.repositry.inventryServiceProxy;
import com.cropdeal.repositry.orderRepostry;


@Service
public class rabbitmqConsumer {
	
	
	private static final Logger logger = LoggerFactory.getLogger(rabbitmqEmitter.class);  
	
	@Autowired
	private mailsenderservice mailsenderservice;
	
	@Autowired
	private orderRepostry orderRepostry;
	
	@Autowired
	private inventryServiceProxy proxy;

	@RabbitListener(queues = "order")
	public void getRabbitmqMsg(Map<String, String> consumemap) throws noProductFoundException {
		
		
		try {
		
		System.out.println(consumemap.entrySet());
		if(consumemap.get("kind").equals("productOrdered")) {
			if(consumemap.get("email")==null) {
				return;
			}
			
			orders orders=orderRepostry.findById(Integer.parseInt(consumemap.get("orderId"))).orElseThrow();
			
			product product=proxy.getProductById(orders.getProductIdList().get(0));
			
			mailsenderservice.sendOrderPlacedMail(consumemap.get("email"),orders,product);
		}else if (consumemap.get("kind").equals("cartOrderedPlaced")) {
			
			orders orders=orderRepostry.findById(Integer.parseInt(consumemap.get("orderId"))).orElseThrow();
			
			List<product> products=new ArrayList<>();
			for(String productid:orders.getProductIdList()) {
				product product=proxy.getProductById(orders.getProductIdList().get(0));
				products.add(product);
			}
			
			mailsenderservice.sendCartOrderPlacedMail(consumemap.get("email"), orders, products);
			
		}else if (consumemap.get("kind").equals("orderCanceled")) {
			
			orders orders=orderRepostry.findById(Integer.parseInt(consumemap.get("orderId"))).orElseThrow();
			
			List<product> products=new ArrayList<>();
			for(String productid:orders.getProductIdList()) {
				product product=proxy.getProductById(orders.getProductIdList().get(0));
				products.add(product);
			}
			
			mailsenderservice.sendOrderCancellationMail(consumemap.get("email"), orders, products);
			
		}
		}catch (Exception e) {
			logger.error("Mail can't be sent because mailsender has thrown some error");
		}
		
		
		
		
		
		
		
		
		
	}
	
}
