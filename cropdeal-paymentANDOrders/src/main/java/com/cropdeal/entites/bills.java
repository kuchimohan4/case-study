package com.cropdeal.entites;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class bills {
	
		@Id
		private int billId;
		private double totalAmount;
		private double payableAmount;
		private String paymentMode;
		private copons coupon;
		private double discountAmount;
		public bills() {
			super();
			// TODO Auto-generated constructor stub
		}
		public int getBillId() {
			return billId;
		}
		public void setBillId(int billId) {
			this.billId = billId;
		}
		public double getTotalAmount() {
			return totalAmount;
		}
		public void setTotalAmount(double totalAmount) {
			this.totalAmount = totalAmount;
		}
		public double getPayableAmount() {
			return payableAmount;
		}
		public void setPayableAmount(double payableAmount) {
			this.payableAmount = payableAmount;
		}
		public String getPaymentMode() {
			return paymentMode;
		}
		public void setPaymentMode(String paymentMode) {
			this.paymentMode = paymentMode;
		}
		public copons getCoupon() {
			return coupon;
		}
		public void setCoupon(copons coupon) {
			this.coupon = coupon;
		}
		public double getDiscountAmount() {
			return discountAmount;
		}
		public void setDiscountAmount(double discountAmount) {
			this.discountAmount = discountAmount;
		}
		public bills(int billId, double totalAmount, double payableAmount, String paymentMode, copons coupon,
				double discountAmount) {
			super();
			this.billId = billId;
			this.totalAmount = totalAmount;
			this.payableAmount = payableAmount;
			this.paymentMode = paymentMode;
			this.coupon = coupon;
			this.discountAmount = discountAmount;
		}
		
		
}

