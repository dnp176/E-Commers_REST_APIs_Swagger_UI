package com.main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.main.entity.Order_Items;
import com.main.entity.Orders;
import com.main.entity.Payments;
import com.main.entity.Products;
import com.main.repository.OrderItemsRepository;
import com.main.repository.OrdersRepository;
import com.main.repository.PaymentsRepository;
import com.main.repository.ProductsRepository;

@Service
public class PaymentsServices {
	
	@Autowired
	PaymentsRepository paymentsRepository;
	
	@Autowired
	OrdersRepository ordersRepository;
	
	@Autowired
	OrderItemsRepository orderItemsRepository;
	
	@Autowired
	ProductsRepository productsRepository;

	@Transactional
    public String updatePaymentStatus(String paymentType, int orderId, String paymentStatus) {
        try {	
            Orders order = ordersRepository.findById(orderId);

            if (order == null) {
                return "Order not found!";
            }

            Payments existingPayment = paymentsRepository.findByOrders(order);
            if (existingPayment != null) {
                return "Payment has already been completed for this order.";
            }
            System.out.println(paymentStatus);
            Payments payment = new Payments();
            switch (paymentStatus) {
				case "Completed": 
					order.setOrder_status(paymentStatus);
		            ordersRepository.save(order);
		            
		            payment.setOrders(order);                 
		            payment.setPaymentStatus(paymentStatus);    
		            payment.setPaymentMethod(paymentType);    
		            paymentsRepository.save(payment);
	
		            return "Payment status updated successfully with this order id: " + orderId;
				
				case "Cancelled":
					List<Order_Items> order_Items = orderItemsRepository.findByOrders(order);
					for (Order_Items item : order_Items) {
				        Products product = item.getProducts();
				        product.setProductStock(product.getProductStock() + item.getProduct_qty());
				        productsRepository.save(product);
				    }
					order.setOrder_status(paymentStatus);
				    ordersRepository.save(order);
				    
		            payment.setOrders(order);                 
		            payment.setPaymentStatus(paymentStatus);    
		            payment.setPaymentMethod(paymentType);    
		            paymentsRepository.save(payment);
				    
					return "Payment cancelled for Order ID: " + orderId;
				case "Failed":
					return "Payment failed for Order ID: " + orderId;
					
				default:
					return "Invalid payment status for Order ID: " + orderId;
			}

        } catch (Exception e) {
            throw new RuntimeException("Error occurred while updating payment status: " + e.getMessage());
        }
    }

	public List<Payments> getAllPaymentInformation() {
		List<Payments> allPayments = paymentsRepository.findAll();
		return allPayments;
	}
	
}
