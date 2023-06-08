package com.cropdeal.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropdeal.entites.cart;
import com.cropdeal.entites.product;
import com.cropdeal.entites.reviews;
import com.cropdeal.exceptions.noProductFoundException;
import com.cropdeal.repositry.cartRepositry;
import com.cropdeal.repositry.productRepositry;
import com.cropdeal.repositry.reviewRepostry;

import jakarta.validation.Valid;

@Service
public class inventryserviceimpl implements inventryService {
	
	@Autowired
	private productRepositry productRepositry;

	@Autowired
	private cartRepositry cartRepositry;
	
	@Autowired
	private reviewRepostry reviewRepostry;
	
	public product addproduct(product product) throws noProductFoundException {
		
		List<product> prodtOptional=productRepositry.findByFarmerIdAndProductName(product.getFarmerId(), product.getProductName());
		if(prodtOptional.size()==0) {
			product.genrateProductId();
			product.setInitialQuantity(product.getAvailableQuantity());
			product.setReviews(new ArrayList<reviews>());
			return productRepositry.save(product);
		}
		else {
			throw new noProductFoundException("a product already exists with name "+product.getProductName()+" try updating quantity of product instad");
			
		}
		
		
	}

	@Override
	public Object updateproduct(product product) throws noProductFoundException {
		List<product> prodtOptional=productRepositry.findByFarmerIdAndProductId(product.getFarmerId(), product.getProductId());
		if(prodtOptional.size()==0) {
			throw new noProductFoundException("no product with name"+product.getProductName());
		}
		else {
			product product2=prodtOptional.get(0);
			return productRepositry.save(new product(product2.getProductId(), product2.getFarmerId(), product.getProductName(), product.getAvailableQuantity(), product2.getInitialQuantity(), product.getDate(), product.getPrice(),product.getProductDetails() , product2.getStatus(), product.getProductImages(), product2.getReviews()));
		}
	}

	@Override
	public void deleteproduct(String id, int formerId) throws noProductFoundException {
		
		List<product> prodtOptional=productRepositry.findByFarmerIdAndProductId(formerId, id);
		if(prodtOptional.size()==0) {
			throw new noProductFoundException("no Such Product exists On ur Account");
		}
		else {
			productRepositry.deleteByProductId(id);
			
		}
		
	}
	
	
	

	@Override
	public cart addtocart(int merchentId, Map<String, String> inputdata) throws noProductFoundException {
		
		product product=getProductById(inputdata.get("productId"));
		
		Optional<cart> cartitem=cartRepositry.findByProductProductIdAndMarchentId(inputdata.get("productId"),merchentId);
		if (cartitem.isPresent()) {
			throw  new noProductFoundException("product already exists in your cart");
		}
		if(Integer.parseInt( inputdata.get("quantity"))>product.getAvailableQuantity()) {
			throw  new noProductFoundException("insuffitiant quantity please enter quantity less than "+product.getAvailableQuantity());
		}
		
		return cartRepositry.save(new cart( Integer.parseInt( inputdata.get("quantity")), merchentId, "incart", LocalDateTime.now(), product));
	}

	@Override
	public product getProductById(String productId) throws noProductFoundException {
		
		List<product> prodtOptional=productRepositry.findByProductId(productId);
		if(prodtOptional.size()==0) {
			throw new noProductFoundException("no Product with product ID"+productId);
		}
		
		return prodtOptional.get(0);
	}

	@Override
	public List<cart> getCartitemsCartsBymarchent(int marchentId) {
		
		return cartRepositry.findByMarchentId(marchentId);
	}

	@Override
	public List<product> getallProducts() {
		// TODO Auto-generated method stub
		return productRepositry.findAll();
	}

	@Override
	public List<product> getallProductsByFarmerId(int farmerId) {
		
		
		return productRepositry.findByFarmerId(farmerId);
	}

	@Override
	public void removefromCart(String productId,int merchentId) throws noProductFoundException {
		Optional<cart> cartitem=cartRepositry.findByProductProductIdAndMarchentId(productId,merchentId);
		cart cart= cartitem.orElseThrow(()-> new noProductFoundException("no such product in your cart"));
		 cartRepositry.deleteByProductProductIdAndMarchentId(productId, merchentId);
		
	}

	@Override
	public void removeAllFromCart(int marchent) {
		cartRepositry.deleteByMarchentId(marchent);
		
	}

	@Override
	public reviews addreview(String productId, reviews reviews,int dealerid) throws noProductFoundException {
		
		boolean orderred=true;
		if(!orderred) {
			throw new noProductFoundException("u havent ordered this product only those who ardered can  add review");
		}
		
		Optional<reviews> revie=	reviewRepostry.findByProductIdAndDealearId(productId, dealerid);
	     
		
	     if(revie.isPresent()) {
	    	 throw  new noProductFoundException("review already added");
	    	 
	     }
	     
		product product=getProductById(productId);
		reviews reviews2=new reviews(gentratereviewid() ,productId,dealerid,reviews.getRating(),reviews.getDescription(),LocalDateTime.now());
		List<reviews> productReviews= product.getReviews();
		productReviews.add(reviews2);
		product.setReviews(productReviews);
		productRepositry.save(product);
		return reviewRepostry.save(reviews2);
		
	}
	
	
	
	@Override
	public reviews updatereview(String productId, reviews reviews,int dealerid) throws noProductFoundException {
		
		Optional<reviews> review=	reviewRepostry.findByProductIdAndDealearId(productId, dealerid);
	     review.orElseThrow(()->new noProductFoundException("no review to update"));
	     reviews reviews2=review.orElse(null);
	     reviews2.setRating(reviews.getRating());
	     reviews2.setDescription(reviews.getDescription());
	     product product=getProductById(productId);
	     List<reviews> productReviews= product.getReviews();
			productReviews.remove(reviews2);
			product.setReviews(productReviews);
			productRepositry.save(product);
		return reviewRepostry.save(reviews2);
		
	}
	

	@Override
	public void removereview(String productid, int dealerid) throws noProductFoundException {
		
	     Optional<reviews> review=	reviewRepostry.findByProductIdAndDealearId(productid, dealerid);
	     review.orElseThrow(()->new noProductFoundException("no review to remove"));
	     product product=getProductById(productid);
	     List<reviews> productReviews= product.getReviews();
			productReviews.remove(review.orElse(null));
			product.setReviews(productReviews);
			productRepositry.save(product);
	    reviewRepostry.deleteByProductIdAndDealearId(productid, dealerid); 
	
	}
	
	
	
	public int gentratereviewid() {
	System.out.println(reviewRepostry.findMaxReviewId().getReviewId());
	return  reviewRepostry.findMaxReviewId().getReviewId()+1;
	}

	
	
	
	
	
}
