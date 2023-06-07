package com.cropdeal.entites;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
@Document
public class BankAccounts {
	@Id
	private int id;
	@NotEmpty
    @Size(max = 100)
    private String accountHolderName;

    @NotEmpty
    @Size(max = 20)
    private String accountNumber;

    @NotEmpty
    @Pattern(regexp = "[A-Z]{4}[0-9]{7}")
    private String ifscCode;
	public BankAccounts() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BankAccounts(int id, String accountHolderName, String accountNumber, String ifscCode) {
		super();
		this.id = id;
		this.accountHolderName = accountHolderName;
		this.accountNumber = accountNumber;
		this.ifscCode = ifscCode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccountHolderName() {
		return accountHolderName;
	}
	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	
	
	
	
}
