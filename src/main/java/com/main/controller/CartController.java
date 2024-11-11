package com.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.main.dto.OrderDTO;
import com.main.entity.Cart;
import com.main.entity.Orders;
import com.main.entity.User;
import com.main.repository.UserRepository;
import com.main.services.CartServices;
import com.main.services.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Cart or Checkout Information", description = "This is used for product cart or checkout")
public class CartController {

	@Autowired
	CartServices cartServices;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OrderService orderService;
	
	@PostMapping("/cart/add")
	@Operation(summary = "Cart Product",description = "Add Product In Cart")
	public ResponseEntity<String> addToCartProduct(@RequestParam int product_id, @RequestParam int product_qty){
		try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); 
            System.out.println(username);
            User currentUser = userRepository.findByEmail(username);
	
			cartServices.addToCart(currentUser.getId(),product_id, product_qty);
			return ResponseEntity.ok("Product added in cart !!!");
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(401).body(e.getMessage());
		}
	}
	
	@PostMapping("/cart/remove")
	@Operation(summary = "Cart Product",description = "Remove Product In Cart")
	public ResponseEntity<String> removeCartProduct(@RequestParam int product_id)
	{
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); 
            System.out.println(username);
            User currentUser = userRepository.findByEmail(username);
            
            cartServices.removeCartProduct(currentUser.getId(),product_id);
			return ResponseEntity.ok("Product remove in cart !!!");
		} catch (Exception e) {
			System.out.println(e);
			return ResponseEntity.status(401).body(e.getMessage());
		}
	}
	
	@GetMapping("/cart")
	@Operation(summary = "Cart Product",description = "Remove Product In Cart")
	public ResponseEntity<List<Cart>> getAllCartProduct() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); 
            User currentUser = userRepository.findByEmail(username);
           
            List<Cart> cartProducts = cartServices.getAllCartProduct(currentUser.getId());
			return ResponseEntity.status(200).body(cartProducts);
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	@PostMapping("/checkout")
	@Operation(summary = "Checkout Product",description = "Create Order")
	public ResponseEntity<String> checkoutOrder() {
	    try {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String username = authentication.getName(); 
	        User currentUser = userRepository.findByEmail(username);

	        String userOrder = cartServices.checkoutOrder(currentUser.getId());

	        return ResponseEntity.status(200).body(userOrder);
	    } catch (Exception e) {
	        System.out.println("Error in checkout process: " + e);
	        return ResponseEntity.status(520).body("Your Order is not created: " + e.getMessage());
	    }
	}
	
	@GetMapping("/orderInfo")
	public ResponseEntity<OrderDTO> getOrderInformation(@RequestParam int orderId){
		OrderDTO orderDTO = orderService.getOrderDetailsByOrderId(orderId);
		return ResponseEntity.status(200).body(orderDTO);
	} 
}
