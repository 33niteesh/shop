package com.shop.DAO;

import java.sql.SQLException;
import java.util.List;

import com.shop.model.*;

public interface ProductDAO {
	Product saveProduct(Product user) throws SQLException;

	Product getById(int id) throws SQLException;

	String deleteById(int id) throws SQLException;

    List<Product> allProducts() throws SQLException;
}

