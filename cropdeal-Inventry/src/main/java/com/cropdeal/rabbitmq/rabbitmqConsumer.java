package com.cropdeal.rabbitmq;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropdeal.entites.product;
import com.cropdeal.mail.mailsenderservice;

@Service
public class rabbitmqConsumer {
	
	@Autowired
	private mailsenderservice mailsenderservice;

	@RabbitListener(queues = "product")
	public void getRabbitmqMsg(Map<String, String> consumemap) {
		
		System.out.println(consumemap.entrySet());
		if(consumemap.get("kind").equals("addedProduct")) {
			if(consumemap.get("email")==null) {
				return;
			}
			product product=new product();
			product.setProductId(consumemap.get("productId"));
			product.setProductName(consumemap.get("productName"));
			product.setAvailableQuantity(Integer.parseInt(consumemap.get("availableQuantity")));
			product.setPrice(Double.parseDouble(consumemap.get("price")));
			product.setProductDetails(consumemap.get("productDetails"));
			mailsenderservice.sendProductAddedMail(consumemap.get("email"),product);
		}else if (consumemap.get("kind").equals("updatedProduct")) {
			
			product product=new product();
			product.setProductId(consumemap.get("productId"));
			product.setProductName(consumemap.get("productName"));
			product.setAvailableQuantity(Integer.parseInt(consumemap.get("availableQuantity")));
			product.setPrice(Double.parseDouble(consumemap.get("price")));
			product.setProductDetails(consumemap.get("productDetails"));
			mailsenderservice.sendProductUpdatedMail(consumemap.get("email"),product);
			
		}else if (consumemap.get("kind").equals("deleteproduct")) {
			
			
			mailsenderservice.sendProductRemovedMail(consumemap.get("email"), consumemap.get("productName"));
			
		}
		
		
		
		
		
	}
	
}
