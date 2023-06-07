package com.cropdeal.entites;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class copons {
	
	@Id
	private int couponId;
	private String coupon;
	private int couponDiscount;
	private int maxLimit;
    private LocalDateTime startDate;
	private LocalDateTime enddate;
	public int getCouponId() {
		return couponId;
	}
	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}
	public String getCoupon() {
		return coupon;
	}
	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
	public int getCouponDiscount() {
		return couponDiscount;
	}
	public void setCouponDiscount(int couponDiscount) {
		this.couponDiscount = couponDiscount;
	}
	public int getMaxLimit() {
		return maxLimit;
	}
	public void setMaxLimit(int maxLimit) {
		this.maxLimit = maxLimit;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEnddate() {
		return enddate;
	}
	public void setEnddate(LocalDateTime enddate) {
		this.enddate = enddate;
	}
	public copons(int couponId, String coupon, int couponDiscount, int maxLimit, LocalDateTime startDate,
			LocalDateTime enddate) {
		super();
		this.couponId = couponId;
		this.coupon = coupon;
		this.couponDiscount = couponDiscount;
		this.maxLimit = maxLimit;
		this.startDate = startDate;
		this.enddate = enddate;
	}
	public copons() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
