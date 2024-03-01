package com.shop.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.shop.model.CustomerProductDetails;
import com.shop.model.Product;

@Repository
public  class CustomerProductServices implements CustomerProductDAO{

	 @Autowired
	 private JdbcTemplate jdbcTemplate;
	 
	 @Override
	 public List<CustomerProductDetails> allCustomers(){
		 List<CustomerProductDetails> cst=new ArrayList<>();
		 
		 return jdbcTemplate.query("select * from customer", (rs, rowNum) -> {
			 List<Product> prd1=jdbcTemplate.query(Constants.GET_USER_PROD, (rd, row) -> {
		        	return new Product(rd.getInt("product_id"), rd.getString("product_name"), rd.getString("prodcut_description"),rd.getBoolean("availability"));
		        },rs.getInt("customer_id"));
	            return new CustomerProductDetails(rs.getInt("customer_id"),rs.getString("contact_number"),rs.getString("customer_name"),rs.getString("email_address"),prd1);
	        });
	    }
	 
	 @Override
	 public List<CustomerProductDetails> getById(int id) {
		 CustomerProductDetails customer=new CustomerProductDetails();
		 Product p= new Product();
		 List<Product> prd1=jdbcTemplate.query(Constants.GET_USER_PROD, (rs, rowNum) -> {
	        	return new Product(rs.getInt("product_id"), rs.getString("product_name"), rs.getString("prodcut_description"),rs.getBoolean("availability"));
	        },id);
		  return jdbcTemplate.query(Constants.GET_USER_BY_ID_QUERY1, (rs, rowNum) -> {
			  	customer.setCustomerId(rs.getInt("customer_id"));
	            customer.setContactNumber(rs.getString("contact_number"));
	            customer.setCustomerName(rs.getString("customer_name"));
	            customer.setEmailAddress(rs.getString("email_address"));
	            customer.setProducts(prd1);
	        	return customer;
	        },id);
	 }
	 
	 @Override
	 public CustomerProductDetails saveCustomerproduct1(CustomerProductDetails prd) {
		    String Stored="CALL insertAll(?,?,?,?)";
		    String bulk="CALL insertProduct(?,?)";
	        jdbcTemplate.update(Stored,prd.getCustomerId(),prd.getContactNumber(),prd.getCustomerName(),prd.getEmailAddress());
	        List<Product> p=prd.getProducts();
	        jdbcTemplate.batchUpdate(bulk, new BatchPreparedStatementSetter() {
	            @Override
	            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
	                preparedStatement.setInt(1, prd.getCustomerId());
	                preparedStatement.setInt(2, p.get(i).getProductId());
	            }

	            @Override
	            public int getBatchSize() {
	                return p.size();
	            }
	        });
//	        for(int i=0;i<p.size();i++) {
//        	jdbcTemplate.update(bulk,prd.getCustomerId(),p.get(i).getProductId());
//        }
	        return prd;
	    }
	 
	 @Override
	    public String deleteById(int id) {
	        Integer i=jdbcTemplate.update(Constants.DELETE_USER_BY_ID1, id);
	        if(i!=1) {
	        	return "product notfound : ";
	        }
	        return "product got deleted with id :" + id;
	    }
}
