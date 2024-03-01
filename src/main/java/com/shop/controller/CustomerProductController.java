package com.shop.controller;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.Exceptions.Conflict;
import com.shop.Exceptions.InternalServerException;
import com.shop.Exceptions.NotFound;
import com.shop.DAO.CustomerProductDAO;
import com.shop.model.CustomerProductDetails;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class CustomerProductController {

	@Autowired
    CustomerProductDAO productdao;

    @GetMapping("/getAllCustomerProductDetails")
    public List<CustomerProductDetails> getAllCustomerProductDetails() {
    	try {
        return productdao.allCustomers();
    	}catch(Exception e) {
    		throw new InternalServerException(e.getMessage());
    	}
    }
    @GetMapping("/customer/{id}")
	  @Operation(summary="get customer by Id")
	 public ResponseEntity<?> getProductsbyId(@PathVariable int id){
	    	try {
	    		List<CustomerProductDetails> products = productdao.getById(id);
	    		JSONObject r=new JSONObject();
	    		r.put("Status", HttpStatus.OK);
	    		r.put("Status code", 200);
	    		r.put("message", "Success");
	    		r.put("Data", products.toString());
	    		return new ResponseEntity<>(r.toString(),HttpStatus.OK);
	    	}
	    	catch (Exception e){
	    		System.out.println(e.getMessage());
	    		return null;
		}
	    }

    @PostMapping("/add_customer")
    @Operation(summary="Add customer")
    public ResponseEntity<?> addUser(@RequestBody CustomerProductDetails user){
    	try {
    		productdao.saveCustomerproduct1(user);
    		return new ResponseEntity<>("customer added",HttpStatus.CREATED);
    	}catch(Exception e) {
    		throw new Conflict(e.getMessage());
    	}

    }
    
    @DeleteMapping("/customer_remove/{id}")
    @Operation(summary="Delete customer by Id")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
    	try {
    		String s=productdao.deleteById(id);
    		return new ResponseEntity<>(s,HttpStatus.OK);
    	}
    	catch (Exception e){
    		System.out.println(e.getMessage());
    		throw new NotFound(e.getMessage());
	}
    }
}
