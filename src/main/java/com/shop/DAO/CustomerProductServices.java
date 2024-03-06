package com.shop.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;

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
		 List<CustomerProductDetails> cst1=jdbcTemplate.query("CALL allData()", (rs, rowNum) -> {
			 	List<Product> prd1=new ArrayList<>();
			 	prd1.add(new Product(rs.getInt("product_id"),rs.getString("product_name"),rs.getString("prodcut_description"),rs.getBoolean("availability")));
	            return new CustomerProductDetails(rs.getInt("customer_id"),rs.getString("contact_number"),rs.getString("customer_name"),rs.getString("email_address"),prd1);
	        });
		 cst1 = cst1.stream()
				    .collect(Collectors.toMap(
				    		CustomerProductDetails::getCustomerId,
				    		Function.identity(),
				    		(existing, replacement) -> {
				    			existing.getProducts().add(replacement.getProducts().get(0));
				    			return existing;
				        }))
				    .values()
				    .stream()
				    .collect(Collectors.toList());
//		 for(int i=0;i<cst1.size()-1;i++) {
//		 if(cst1.get(i).customerId==cst1.get(i+1).customerId) {
//			 cst1.get(i).products.add(cst1.get(i+1).products.get(0));
//			 cst1.remove(cst1.get(i+1));
//		 }
//	 }
		 return cst1;
		 
//		Previous
//		 return jdbcTemplate.query("select * from customer", (rs, rowNum) -> {
//			 List<Product> prd1=jdbcTemplate.query(Constants.GET_USER_PROD, (rd, row) -> {
//		        	return new Product(rd.getInt("product_id"), rd.getString("product_name"), rd.getString("prodcut_description"),rd.getBoolean("availability"));
//		        },rs.getInt("customer_id"));
//	            return new CustomerProductDetails(rs.getInt("customer_id"),rs.getString("contact_number"),rs.getString("customer_name"),rs.getString("email_address"),prd1);
//	        });
		 
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
	 public CustomerProductDetails Customerproductsave(CustomerProductDetails prd) {
		    String Stored="CALL insertAll(?,?,?,?,?,?,?)";
		    StringJoiner joiner1 = new StringJoiner(",");
		    StringJoiner joiner2 = new StringJoiner(",");
		    prd.getProducts().forEach(product -> {
		        joiner1.add(String.valueOf(prd.getCustomerId()));
		        joiner2.add(String.valueOf(product.getProductId()));
		    });
		    jdbcTemplate.update(Stored,prd.getCustomerId(),prd.getContactNumber(),prd.getCustomerName(),prd.getEmailAddress(),prd.getProducts().size(),String.valueOf(joiner1),String.valueOf(joiner2));
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
// <----------------------procedure for insert in to customer,bulk insert to customer_product procedure-------------->
// CREATE DEFINER=`root`@`localhost` PROCEDURE `insertAll`(IN customer_id int,IN contact_number varchar(100),IN customer_name varchar(200),IN email_address varchar(200),IN size int,IN text1 TEXT,IN text2 TEXT)
// BEGIN
// 	INSERT INTO customer values(customer_id,contact_number,customer_name,email_address);
//     CALL InsertCustomerProduct(size,text1,text2);
// END
//<------------------procedure for customer_product bulk update--------------->
// CREATE DEFINER=`root`@`localhost` PROCEDURE `InsertCustomerProduct`(
//     IN n INT, -- Parameter for the number of values to insert
//     IN cust_id_list VARCHAR(255), -- Comma-separated list of customer IDs
//     IN prod_id_list VARCHAR(255)  -- Comma-separated list of product IDs
// )
	
// BEGIN
//     DECLARE i INT DEFAULT 1;
//     DECLARE cust_id INT;
//     DECLARE prod_id INT;

//     -- Loop through the values and insert them into the customer_product table
//     WHILE i <= n DO
//         -- Extract individual customer and product IDs from the lists
//         SET cust_id = CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(cust_id_list, ',', i), ',', -1) AS UNSIGNED);
//         SET prod_id = CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(prod_id_list, ',', i), ',', -1) AS UNSIGNED);

//         -- Insert the values into the customer_product table
//         INSERT INTO customer_product (customer_id, product_id)
//         VALUES (cust_id, prod_id); -- Assuming a default quantity of 1 for simplicity

//         -- Increment the loop counter
//         SET i = i + 1;
//     END WHILE;
// END
