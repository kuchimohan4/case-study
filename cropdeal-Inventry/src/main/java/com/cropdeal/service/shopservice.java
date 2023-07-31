package com.cropdeal.service;

import java.util.List;

import com.cropdeal.entites.shop;
import com.cropdeal.exceptions.noshopfoundexception;

public interface shopservice {

	shop newshop(shop shop, int i) throws noshopfoundexception;

	shop updateShop(shop shop, int requestUserId) throws noshopfoundexception;

	List<shop> gettallshops();

	shop getShopById(int id) throws noshopfoundexception ;
	public boolean doesFarmerHaveShop(int farmerId);

	

}
