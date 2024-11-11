package com.main.common;

import java.util.List;

import org.springframework.stereotype.Component;

import com.main.entity.Cart;
import com.main.entity.Products;

@Component
public class CommonMethods {
	
	public void checkProductAvailability(List<Cart> orderDetails) {
	    orderDetails.forEach(cart -> {
	        Products product = cart.getProducts();
	        int availableQty = product.getProductStock();
	        int requiredQty = cart.getProduct_qty();
	        
	        // Check if required quantity is greater than available stock
	        if (requiredQty > availableQty) {
	            throw new RuntimeException("Insufficient stock for product: " + product.getProductName() +
	                    ". Available quantity: " + availableQty + ", Requested quantity: " + requiredQty);
	        }
	    });
	}

}
