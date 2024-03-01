package com.shop.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="customer")
public class CustomerProductDetails {
	@Id
	public int customerId;
	public String customerName;
	public String contactNumber;
	public String emailAddress;
	 @ManyToMany
	    @JoinTable(
	        name = "CustomerProduct",
	        joinColumns = @JoinColumn(name = "customerId"),
	        inverseJoinColumns = @JoinColumn(name = "productId")
	    )
	public List<Product> products;
	public int getCustomerId() {
		return customerId;
	}
	public CustomerProductDetails(int customerId, String customerName, String contactNumber, String emailAddress,List<Product> product) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.contactNumber = contactNumber;
		this.emailAddress = emailAddress;
		this.products=product;
	}
	public CustomerProductDetails() {
	}
	@Override
	public String toString() {
		return "{customerId:" + customerId + ", customerName:" + customerName
				+ ", contactNumber:" + contactNumber + ", emailAddress:" + emailAddress + ", products:" + products
				+ "}";
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
}
