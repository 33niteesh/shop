package com.shop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="product")
public class Product {
	@Override
	public String toString() {
		return "{ productId:" + productId + ", productName:" + productName + ", prodcutDescription:"
				+ prodcutDescription + ", availability:" + availability + "}";
	}
	@Id
	public int productId;
	public String productName;
	public String prodcutDescription;
	public boolean availability;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProdcutDescription() {
		return prodcutDescription;
	}
	public void setProdcutDescription(String prodcutDescription) {
		this.prodcutDescription = prodcutDescription;
	}
	public boolean isAvailability() {
		return availability;
	}
	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
	public Product(int productId, String productName, String prodcutDescription, boolean availability) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.prodcutDescription = prodcutDescription;
		this.availability = availability;
	}
	public Product() {}
}
