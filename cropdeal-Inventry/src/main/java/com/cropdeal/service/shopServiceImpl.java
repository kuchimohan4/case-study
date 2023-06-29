package com.cropdeal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropdeal.entites.shop;
import com.cropdeal.exceptions.noshopfoundexception;
import com.cropdeal.repositry.shoprepositry;


@Service
public class shopServiceImpl implements shopservice {
	
	@Autowired
	private shoprepositry shoprepositry;

	@Override
	public shop newshop(shop usershop,int shopid) throws noshopfoundexception{
		
//		shoprepositry.findById(shopid).ifPresent((shopdb)->{
//			throw new noshopfoundexception("shop alreay exists please update instad");
//			});
		System.out.println(usershop.getShopName());
		if(shoprepositry.findById(shopid).isPresent()) {
			throw new noshopfoundexception("shop alreay exists please update instad");
		}
		
		usershop.setShopid(shopid);
		return shoprepositry.save(usershop);
	}

	@Override
	public shop updateShop(shop shop, int shopid) throws noshopfoundexception {
		
		if(shoprepositry.findById(shopid).isEmpty()) {
			throw new noshopfoundexception("no shop fount please add instad");
		}
		
		shop.setShopid(shopid);
		return shoprepositry.save(shop);
	}

	@Override
	public List<shop> gettallshops() {
		// TODO Auto-generated method stub
		return shoprepositry.findAll();
	}

	@Override
	public shop getShopById(int id) throws noshopfoundexception {
		
		return shoprepositry.findById(id).orElseThrow(()->new noshopfoundexception("no shop with id"+id));
	}

}
