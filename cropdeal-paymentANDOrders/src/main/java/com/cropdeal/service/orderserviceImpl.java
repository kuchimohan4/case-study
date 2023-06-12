package com.cropdeal.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.grammars.hql.HqlParser.CubeContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.cropdeal.entites.bills;
import com.cropdeal.entites.copons;
import com.cropdeal.entites.orders;
import com.cropdeal.entites.transactions;
import com.cropdeal.exception.invalidQuantityException;
import com.cropdeal.exception.noProductFoundException;
import com.cropdeal.models.cart;
import com.cropdeal.models.product;
import com.cropdeal.models.transactionDetails;
import com.cropdeal.rabbitmq.rabbitmqEmitter;
import com.cropdeal.repositry.billRepostry;
import com.cropdeal.repositry.coponRepositry;
import com.cropdeal.repositry.inventryServiceProxy;
import com.cropdeal.repositry.orderRepostry;
import com.cropdeal.repositry.transactionRepostry;
import com.razorpay.RazorpayException;

@Service
public class orderserviceImpl implements orderService {
	
	@Autowired
	private billRepostry billRepostry;
	@Autowired
	private coponRepositry coponRepositry;
	@Autowired
	private orderRepostry orderRepostry;
	@Autowired
	private inventryServiceProxy proxy;
	@Autowired
	private paymentService paymentService;
	
	@Autowired
	private rabbitmqEmitter rabbitmqEmitter;
	
	@Autowired
	private transactionRepostry transactionRepostry;
	

	@Override
	public transactionDetails placeOrder(int dealearid, Map<String, String> inputMap) throws noProductFoundException, invalidQuantityException, RazorpayException {
		
//		productId
//		quantity
//		copon
	
		product product= proxy.getProductById(inputMap.get("productId"));
		
		if (product.getAvailableQuantity()<Integer.parseInt(inputMap.get("quantity"))) {
			throw new invalidQuantityException("Invalid quantity please enter quantity less than "+product.getAvailableQuantity());
			
		}
		String copon=inputMap.get("copon");
	    double	discount=0;
	    double totalamount=Integer.parseInt(inputMap.get("quantity"))*product.getPrice();
		if(copon=="" || copon==null) {
			
		}else {
			Optional<copons> coponoptional =coponRepositry.findByCoupon(copon);
			copons coponfromdb=coponoptional.orElseThrow(()-> new noProductFoundException("Invalid Copon"));
			
			 discount=coponfromdb.getMaxLimit()<coponfromdb.getCouponDiscount()*totalamount/100 ?  coponfromdb.getMaxLimit():coponfromdb.getCouponDiscount()*totalamount/100;
			
		}
		
		transactionDetails transactionDetail= paymentService.getPayment((totalamount-discount)*100);
		
		List<Integer>  farmeridlist=new ArrayList<>();
		farmeridlist.add(product.getFarmerId());
		
		transactions transactions=new transactions(transactionDetail.getOrderId(),"pending",dealearid,farmeridlist,Integer.parseInt(transactionDetail.getAmount())/100,LocalDateTime.now());
		
		transactionRepostry.save(transactions);
		
		orders order=genarateOrder(product,Integer.parseInt(inputMap.get("quantity")),dealearid,transactions,inputMap.get("copon"),totalamount);
		
		
		return transactionDetail;
	}









	@Override
	public orders paymentConformation(Map<String, String> paymentdetails) throws noProductFoundException {
		
		
		Optional<transactions> transactions =transactionRepostry.findById(paymentdetails.get("orderid"));
		
		transactions transactions2= transactions.orElse(new transactions());
		
		if(!transactions2.getStatus().equalsIgnoreCase("pending")) {
			
			throw new noProductFoundException("sorry for inconvinance but there is an issue ");
		}
		
		
		transactions2.setRazorpay_payment_id(paymentdetails.get("razorpay_payment_id"));
		transactions2.setRazorpay_order_id(paymentdetails.get("razorpay_order_id"));
		transactions2.setRazorpay_signature(paymentdetails.get("razorpay_signature"));
		transactions2.setStatus("success");
		transactionRepostry.save(transactions2);
		
		Optional<orders> order=orderRepostry.findByTransactionsTransactionId(transactions2.getTransactionId());
		
		orders orderDb=order.orElseThrow(()-> new noProductFoundException("sorry for inconvininance there is some error with transaction"));
		
		Map<String, String> orderMap=new HashMap<>();
		orderMap.put("productId", orderDb.getProductIdList().get(0));
		orderMap.put("quantity",""+orderDb.getQuantity().get(0));
		
		proxy.orderPlaced(orderMap);
		
		orderDb.setTransactions(transactions2);
		orderDb.setStatus("success");
		
		
		
//		mailling
		Map<String, String> emmitmap=new HashMap<>();
		emmitmap.put("type", "needMail");
		emmitmap.put("kind", "productOrdered");
		emmitmap.put("userId", ""+orderDb.getMarchentId());
		emmitmap.put("queue", "order");
		emmitmap.put("orderId", ""+orderDb.getOrderId());
		
		
		rabbitmqEmitter.emmitmsg(emmitmap);
		
		
//		orderid
//		razorpay_payment_id
//		razorpay_order_id
//		razorpay_signature
		
		
		return orderRepostry.save(orderDb);
	}


	private orders genarateOrder(product product, int quantity,int dealearid,transactions transaction,String copon,double totalamount) throws noProductFoundException {
		
		List<Integer>  quantitylist=new ArrayList<>();
		quantitylist.add(quantity);
		List<String>  productiList=new ArrayList<>();
		productiList.add(product.getProductId());
		orders orders=new orders(quantitylist,dealearid,"pending",LocalDateTime.now(),productiList,transaction);
		
		
		
		bills bills;
		if(copon=="" || copon==null) {
			bills=new bills( transaction.getAmount(), transaction.getAmount(), "Razopay",0);
			
		}else {
			Optional<copons> coponoptional =coponRepositry.findByCoupon(copon);
			copons coponfromdb=coponoptional.orElseThrow(()-> new noProductFoundException("Invalid Copon"));
//			double discount=coponfromdb.getMaxLimit()<coponfromdb.getCouponDiscount()*transaction.getAmount()/100 ?  coponfromdb.getMaxLimit():coponfromdb.getCouponDiscount()*transaction.getAmount()/100;
			bills=new bills(totalamount,transaction.getAmount(),"Razopay",coponfromdb,totalamount-transaction.getAmount());
		}
		System.out.println("hello");
		billRepostry.save(bills);
		orders.setBill(bills);
		System.out.println("heloo");
		
		System.out.println();
		return orderRepostry.save(orders);
	}






	@Override
	public transactionDetails orderCartProducts(int dealearid,Map<String, String> inputMap) throws noProductFoundException, invalidQuantityException, RazorpayException {
		
//		copon
		
		List<cart> cartlist=proxy.getCartItemsByMarchentprox(dealearid);
		
		double totalamount=0;
		List<Integer>  farmeridlist=new ArrayList<>();
		List<String>  producidtlist=new ArrayList<>();
		List<Integer>  quantitylist=new ArrayList<>();
		for(cart cart:cartlist) {
			product product= cart.getProduct();
			producidtlist.add(product.getProductId());
			farmeridlist.add(product.getFarmerId());
			quantitylist.add(cart.getQuantity());
			totalamount=totalamount+cart.getQuantity()*product.getPrice();
			if (product.getAvailableQuantity()<cart.getQuantity()) {
				throw new invalidQuantityException("Invalid quantity please enter quantity less than "+product.getAvailableQuantity());
				
			}
		}
		if (totalamount==0) {
			throw new invalidQuantityException("no products in ur cart to place order");
			
		}
		String copon=inputMap.get("copon");
		double discount=0;
		if(copon=="" || copon==null) {
			
		}else {
			Optional<copons> coponoptional =coponRepositry.findByCoupon(copon);
			copons coponfromdb=coponoptional.orElseThrow(()-> new noProductFoundException("Invalid Copon"));
			
			 discount=coponfromdb.getMaxLimit()<coponfromdb.getCouponDiscount()*totalamount/100 ?  coponfromdb.getMaxLimit():coponfromdb.getCouponDiscount()*totalamount/100;
			
		}
		transactionDetails transactionDetail= paymentService.getPayment((totalamount-discount)*100);
		
		transactions transactions=new transactions(transactionDetail.getOrderId(),"pending",dealearid,farmeridlist,Integer.parseInt(transactionDetail.getAmount())/100,LocalDateTime.now());
		transactionRepostry.save(transactions);
		orders order=genarateOrderforCart(producidtlist,quantitylist,dealearid,transactions,inputMap.get("copon"),totalamount);
		
		
		return transactionDetail;
	}



	private orders genarateOrderforCart(List<String> producidtlist, List<Integer> quantitylist, int dealearid,
			transactions transaction, String copon,double totalamount) throws noProductFoundException {
		// TODO Auto-generated method stub
		orders orders=new orders(quantitylist,dealearid,"pending",LocalDateTime.now(),producidtlist,transaction);
		
		bills bills;
		if(copon=="" || copon==null) {
			bills=new bills( transaction.getAmount(), transaction.getAmount(), "Razopay",0);
			
		}else {
			Optional<copons> coponoptional =coponRepositry.findByCoupon(copon);
			copons coponfromdb=coponoptional.orElseThrow(()-> new noProductFoundException("Invalid Copon"));
			
//			double discount=coponfromdb.getMaxLimit()<coponfromdb.getCouponDiscount()*transaction.getAmount()/100 ?  coponfromdb.getMaxLimit():coponfromdb.getCouponDiscount()*transaction.getAmount()/100;
			bills=new bills(totalamount,transaction.getAmount(),"Razopay",coponfromdb,totalamount-transaction.getAmount());
		}
		
		billRepostry.save(bills);
		orders.setBill(bills);
		return orderRepostry.save(orders);
		
		
	}


	@Override
	public orders cartPaymentConformation(Map<String, String> paymentdetails) throws noProductFoundException {

		Optional<transactions> transactions =transactionRepostry.findById(paymentdetails.get("orderid"));
		
		transactions transactions2= transactions.orElse(new transactions());
		
		if(!transactions2.getStatus().equalsIgnoreCase("pending")) {
			
			throw new noProductFoundException("sorry for inconvinance but there is an issue ");
		}
		
		transactions2.setRazorpay_payment_id(paymentdetails.get("razorpay_payment_id"));
		transactions2.setRazorpay_order_id(paymentdetails.get("razorpay_order_id"));
		transactions2.setRazorpay_signature(paymentdetails.get("razorpay_signature"));
		transactions2.setStatus("success");
		transactionRepostry.save(transactions2);
		
		Optional<orders> order=orderRepostry.findByTransactionsTransactionId(transactions2.getTransactionId());
		
		orders orderDb=order.orElseThrow(()-> new noProductFoundException("sorry for inconvininance there is some error with transaction"));
		
		Map<String, String> orderMap=new HashMap<>();
		List<String> productidList =orderDb.getProductIdList();
		List<Integer> quantityList= orderDb.getQuantity();
		
		for (int i=0;productidList.size()>i;i++) {
			
			orderMap.put(productidList.get(i),""+quantityList.get(i));
		
			
		}
		
		
		
		proxy.cartOrderplaced(orderMap,transactions2.getDealearId());
		
		orderDb.setTransactions(transactions2);
		orderDb.setStatus("success");
		
		
		
		
//		mailling
		Map<String, String> emmitmap=new HashMap<>();
		emmitmap.put("type", "needMail");
		emmitmap.put("kind", "cartOrderedPlaced");
		emmitmap.put("userId", ""+orderDb.getMarchentId());
		emmitmap.put("queue", "order");
		emmitmap.put("orderId", ""+orderDb.getOrderId());
		
		
		rabbitmqEmitter.emmitmsg(emmitmap);
		
		
		
		
//		orderid
//		razorpay_payment_id
//		razorpay_order_id
//		razorpay_signature
		
		
		return orderRepostry.save(orderDb);
		
		
//		return null;
	}
	
	

	@Override
	public orders cancelorder(int orderId, int dealearid) throws noProductFoundException {
		
		orders orders=orderRepostry.findByMarchentIdAndOrderIdAndStatus(dealearid, orderId,"success").orElseThrow(()-> new noProductFoundException("no order with orderId"+orderId));
		
		Map<String, String> orderMap=new HashMap<>();
		List<String> productidList =orders.getProductIdList();
		List<Integer> quantityList= orders.getQuantity();
		
		for (int i=0;productidList.size()>i;i++) {
			
			orderMap.put(productidList.get(i),""+quantityList.get(i));
			
		}
		proxy.orderCanceled(orderMap);
		orders.setStatus("canceled");
		orderRepostry.save(orders);
		
		
//		mailling
		Map<String, String> emmitmap=new HashMap<>();
		emmitmap.put("type", "needMail");
		emmitmap.put("kind", "orderCanceled");
		emmitmap.put("userId", ""+orders.getMarchentId());
		emmitmap.put("queue", "order");
		emmitmap.put("orderId", ""+orders.getOrderId());
		
		
		rabbitmqEmitter.emmitmsg(emmitmap);
		
		return orders;
	}
	
	
	
	

}
