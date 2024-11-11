package com.main.services;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.main.common.CommonMethods;
import com.main.entity.Cart;
import com.main.entity.Order_Items;
import com.main.entity.Orders;
import com.main.entity.Products;
import com.main.entity.User;
import com.main.repository.CartRepository;
import com.main.repository.OrderItemsRepository;
import com.main.repository.OrdersRepository;
import com.main.repository.ProductsRepository;
import com.main.repository.UserRepository;

@Service
public class CartServices {
	
	@Autowired
	ProductsRepository productsRepository;
	
	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OrdersRepository ordersRepository;
	
	@Autowired
	OrderItemsRepository orderItemsRepository;
	
	@Autowired
	CommonMethods commonMethods;
	
	public void addToCart(int userId, int product_id, int product_qty) {
		User user = userRepository.findById(userId).get();
		Products product = productsRepository.findById(product_id);
		System.out.println(product);
		if (user == null) {
		    throw new RuntimeException("User not found");
		}

		if (product == null) {
		    throw new RuntimeException("Product not found");
		}
		
		if(product.getProductStock() < product_qty ) {
			throw new RuntimeException("Insufficient stock for product: " + product.getProductName() +
                    ". Available quantity: " + product.getProductStock() + ", Requested quantity: " + product_qty);
		}

        Cart cartItem = new Cart();
        cartItem.setUser(user);
        cartItem.setProducts(product);
        cartItem.setProduct_qty(product_qty);

        cartRepository.save(cartItem);
	}

	@Transactional
	public void removeCartProduct(int userId, int product_id) {
		User user = userRepository.findById(userId).get();
		Products product = productsRepository.findById(product_id);

		if (user == null) {
		    throw new RuntimeException("User not found");
		}

		if (product == null) {
		    throw new RuntimeException("Product not found");
		}
		cartRepository.deleteByUserAndProducts(user,product);
		System.out.println(product_id);
	}

	public List<Cart> getAllCartProduct(int userId) {
		List allProducts = cartRepository.findByUserId(userId);
		return allProducts;
	}
	
	@Transactional
	public String checkoutOrder(int userId) {
	    try {
	        User user = userRepository.findById(userId)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        List<Cart> orderDetails = Optional.ofNullable(cartRepository.findByUserId(userId))
	                .filter(carts -> !carts.isEmpty())
	                .orElseThrow(() -> new RuntimeException("Cart is empty"));
	        
	     
	        commonMethods.checkProductAvailability(orderDetails);

	        BigDecimal totalAmount = orderDetails.stream()
	                .map(cart -> cart.getProducts().getProductPrice().multiply(BigDecimal.valueOf(cart.getProduct_qty())))
	                .reduce(BigDecimal.ZERO, BigDecimal::add);
	        
	        int orderId = generateUniqueOrderId();

	        // Create the new order
	        Orders newOrder = new Orders();
	        newOrder.setId(orderId); 
	        newOrder.setUser(user);
	        newOrder.setTotal_amount(totalAmount.intValue());
	        newOrder.setOrder_status("Pending");
	        newOrder.setCreatedDate(ZonedDateTime.now());
	        ordersRepository.save(newOrder);

	        // Add cart items to order_items table
			List<Order_Items> orderItemsList = orderDetails.stream().map(cart -> {
				Products product = cart.getProducts();
				int orderedQty = cart.getProduct_qty();
				product.setProductStock(product.getProductStock() - orderedQty);

				productsRepository.save(product);

				// Create order items
				Order_Items orderItem = new Order_Items();
				orderItem.setOrders(newOrder);
				orderItem.setProducts(product);
				orderItem.setProduct_qty(orderedQty);
				orderItem.setProductPrice(product.getProductPrice());
				return orderItem;
			}).toList();

	        orderItemsRepository.saveAll(orderItemsList);

	        cartRepository.deleteByUserId(userId);

	        return "Order placed successfully with Order ID: " + orderId + " and Total Amount: " + totalAmount;
	    } catch (RuntimeException e) {
	        return "Error while processing the order: " + e.getMessage();
	    } catch (Exception e) {
	        return "An unexpected error occurred: " + e.getMessage();
	    }
	}
	
	
	public BigDecimal calculateTotalOrderAmount(List<Cart> cartItems) {
        return cartItems.stream()
                .map(item -> item.getProducts().getProductPrice().multiply(BigDecimal.valueOf(item.getProduct_qty())))
                .reduce(BigDecimal.ZERO, BigDecimal::add); 
    }
	
	private int generateUniqueOrderId() {
        Random random = new Random();
        int orderId;
        boolean exists;

        
        do {
            orderId = random.nextInt(90000) + 10000; 
            exists = ordersRepository.existsById(orderId);
        } while (exists);

        return orderId;
    }
	
	
}
