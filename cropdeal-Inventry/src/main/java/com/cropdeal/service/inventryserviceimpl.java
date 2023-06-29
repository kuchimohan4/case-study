package com.cropdeal.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cropdeal.entites.cart;
import com.cropdeal.entites.product;
import com.cropdeal.entites.reviews;
import com.cropdeal.exceptions.noProductFoundException;
import com.cropdeal.rabbitmq.rabbitmqEmitter;
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
	
	@Autowired
	private rabbitmqEmitter rabbitmqEmitter;
	
	public product addproduct(int formerId,product product) throws noProductFoundException {
		product.setFarmerId(formerId);
		List<product> prodtOptional=productRepositry.findByFarmerIdAndProductName(product.getFarmerId(), product.getProductName());
		if(!(prodtOptional.size()==0)) {
			throw new noProductFoundException("a product already exists with name "+product.getProductName()+" try updating quantity of product instad");
			
		}
		product.genrateProductId();
		product.setInitialQuantity(product.getAvailableQuantity());
		product.setReviews(new ArrayList<reviews>());
		
		
//		mailling
		Map<String, String> emmitmap=new HashMap<>();
		emmitmap.put("type", "needMail");
		emmitmap.put("kind", "addedProduct");
		emmitmap.put("userId", ""+product.getFarmerId());
		emmitmap.put("queue", "product");
		emmitmap.put("productId", product.getProductId());
		emmitmap.put("productName", product.getProductName());
		emmitmap.put("availableQuantity", ""+product.getAvailableQuantity());
		emmitmap.put("price", ""+product.getPrice());
		emmitmap.put("productDetails", product.getProductDetails());
		
		rabbitmqEmitter.emmitmsg(emmitmap);
		
		
		return productRepositry.save(product);
		
		
	}

	@Override
	public Object updateproduct(product product,int farmerId) throws noProductFoundException {
		List<product> prodtOptional=productRepositry.findByFarmerIdAndProductId(farmerId, product.getProductId());
		if(prodtOptional.size()==0) {
			throw new noProductFoundException("no product with name"+product.getProductName());
		}
		else {
			product product2=prodtOptional.get(0);
			
//			maill
			Map<String, String> emmitmap=new HashMap<>();
			emmitmap.put("type", "needMail");
			emmitmap.put("kind", "updatedProduct");
			emmitmap.put("userId", ""+product2.getFarmerId());
			emmitmap.put("queue", "product");
			emmitmap.put("productId", product2.getProductId());
			emmitmap.put("productName", product.getProductName());
			emmitmap.put("availableQuantity", ""+product.getAvailableQuantity());
			emmitmap.put("price", ""+product.getPrice());
			emmitmap.put("productDetails", product.getProductDetails());
			
			rabbitmqEmitter.emmitmsg(emmitmap);
			
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
//			maill
			Map<String, String> emmitmap=new HashMap<>();
			emmitmap.put("type", "needMail");
			emmitmap.put("kind", "deleteproduct");
			emmitmap.put("userId", ""+prodtOptional.get(0).getFarmerId());
			emmitmap.put("queue", "product");
			emmitmap.put("productName", prodtOptional.get(0).getProductName());
			
			
			rabbitmqEmitter.emmitmsg(emmitmap);
			productRepositry.deleteByProductId(id);
			
		}
		
	}
	
	
	

	@Override
	public cart addtocart(int merchentId, Map<String, String> inputdata) throws noProductFoundException {
		
		product product=getProductById(inputdata.get("productId"));
		
		Optional<cart> cartitem=cartRepositry.findByProductProductIdAndMarchentId(inputdata.get("productId"),merchentId);
		if (cartitem.isPresent()) {
//			throw  new noProductFoundException("product already exists in your cart");
			System.out.println("cart't presrt");
			if(Integer.parseInt( inputdata.get("quantity"))+cartitem.get().getQuantity()>product.getAvailableQuantity()) {
				throw  new noProductFoundException("insuffitiant quantity please enter quantity less than "+product.getAvailableQuantity());
			}
			cart cartitemdbCart=cartitem.get();
			cartitemdbCart.setQuantity(Integer.parseInt( inputdata.get("quantity"))+cartitem.get().getQuantity());
			return cartRepositry.save(cartitemdbCart);
		}
		if(Integer.parseInt( inputdata.get("quantity"))>product.getAvailableQuantity()) {
			throw  new noProductFoundException("insuffitiant quantity please enter quantity less than "+product.getAvailableQuantity());
		}
		System.out.println("cart presrt");
		return cartRepositry.save(new cart( Integer.parseInt( inputdata.get("quantity")), merchentId, "incart", LocalDateTime.now(), product));
	}
	
	@Override
	public cart reduceProductsFromcart(int merchentId, Map<String, String> inputdata) throws noProductFoundException {
		product product=getProductById(inputdata.get("productId"));
		Optional<cart> cartitem=cartRepositry.findByProductProductIdAndMarchentId(inputdata.get("productId"),merchentId);
		if (cartitem.isPresent()) {
			if(cartitem.get().getQuantity()-Integer.parseInt( inputdata.get("quantity"))<0) {
				throw  new noProductFoundException("u dont have enpogh of this products to remove"+product.getAvailableQuantity());
			}
			cart cartitemdbCart=cartitem.get();
			cartitemdbCart.setQuantity(cartitem.get().getQuantity()-Integer.parseInt( inputdata.get("quantity")));
			return cartRepositry.save(cartitemdbCart);
		}else {
			throw new noProductFoundException("u dont have  this product in ur cart");
		}
		
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
		reviews reviews2=new reviews(gentratereviewid() ,productId,product.getFarmerId(),dealerid,reviews.getRating(),reviews.getDescription(),LocalDateTime.now());
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
		
		return  reviewRepostry.findReviewWithMaxReviewId().getReviewId()+1;
	}

	@Override
	public String orderPlaced(Map<String, String> orderdetails) throws noProductFoundException {
		
//		productId
//		quantity
	    product products = productRepositry.findByProductId(orderdetails.get("productId")).get(0) ;
		
	    
	    if(products.getAvailableQuantity() < Integer.parseInt(orderdetails.get("quantity"))){
	    	throw new noProductFoundException("sorry for inconvinace but we have insffitiant ");
	    }
	    
	    products.setAvailableQuantity(products.getAvailableQuantity()-Integer.parseInt(orderdetails.get("quantity")) );
		
	    productRepositry.save(products);
		return "success";
	}

	@Override
	public String cartOrderplaced(Map<String, String> orderdetails,int marchentId) throws noProductFoundException {

		for (Map.Entry<String, String> entry : orderdetails.entrySet()) {
//	    System.out.println("Product ID: " + entry.getKey() + ", Quantity: " + entry.getValue());
			product products = productRepositry.findByProductId(entry.getKey()).get(0);
			if (products.getAvailableQuantity() < Integer.parseInt(entry.getValue())) {
				throw new noProductFoundException(
						"sorry for inconvinace but we have insffitiant quantity to place your order refund will be insiated shortly");
			}
			products.setAvailableQuantity(products.getAvailableQuantity() - Integer.parseInt(entry.getValue()));
			productRepositry.save(products);

		}
		removeAllFromCart(marchentId);

		return "success";
	}

	@Override
	public String orderCanceled(Map<String, String> orderdetails) throws noProductFoundException {
		
		for (Map.Entry<String, String> entry : orderdetails.entrySet()) {
//		    System.out.println("Product ID: " + entry.getKey() + ", Quantity: " + entry.getValue());
				product products = productRepositry.findByProductId(entry.getKey()).get(0);
				products.setAvailableQuantity(products.getAvailableQuantity() + Integer.parseInt(entry.getValue()));
				productRepositry.save(products);

			}

			return "success";
		
		
		
	}

	@Override
	public Double getavgreviewofshop(int id) {
		Double avgRating = reviewRepostry.getAverageRatingByFarmerId(id);
	    if (avgRating == null) {
	    	return 0.0;
	       
	    }
		return avgRating;
		
	}
	
	@Override
	public double getavgRatingOfProduct(String productId) {
		 Double avgRating = reviewRepostry.getAverageRatingByProductId(productId);
		    if (avgRating == null) {
		    	return 0.0;
		       
		    }
		    return avgRating;
	}
	
	
	

	
	
	
	
	
}
