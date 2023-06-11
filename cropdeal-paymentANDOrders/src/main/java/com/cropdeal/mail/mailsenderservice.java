package com.cropdeal.mail;

import java.util.List;

import com.cropdeal.entites.orders;
import com.cropdeal.models.product;

public interface mailsenderservice {

	

	public void sendOrderPlacedMail(String dealerEmail, orders order, product product);
	public void sendCartOrderPlacedMail(String dealerEmail, orders order, List<product> products);
	public void sendOrderCancellationMail(String dealerEmail, orders order, List<product> products);
}
