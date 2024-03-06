package com.shop.DAO;

import java.sql.SQLException;
import java.util.List;

import com.shop.model.CustomerProductDetails;
import com.shop.model.Product;

public interface CustomerProductDAO {
	List<CustomerProductDetails> allCustomers() throws SQLException;
	List<CustomerProductDetails> getById(int id) throws SQLException;
	String deleteById(int id) throws SQLException;
	CustomerProductDetails Customerproductsave(CustomerProductDetails user) throws SQLException;
	CustomerProductDetails saveCustomerproduct1(CustomerProductDetails user) throws SQLException;
}
