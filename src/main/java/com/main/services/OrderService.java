package com.main.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.dto.OrderDTO;
import com.main.dto.OrderItemDTO;
import com.main.dto.UserDTO;
import com.main.entity.Order_Items;
import com.main.entity.Orders;
import com.main.repository.OrderItemsRepository;
import com.main.repository.OrdersRepository;

@Service
public class OrderService {
	
	@Autowired
	OrdersRepository ordersRepository;
	
	@Autowired
	OrderItemsRepository orderItemsRepository;
	
	public OrderDTO getOrderDetailsByOrderId(int orderId) {
        // Fetch the order by orderId
		Orders order = ordersRepository.findById(orderId);
//		Orders order = orderItemsRepository.findOrderWithUser(orderId);
		System.out.println(order.getUser());
		if (order == null) {
		    throw new RuntimeException("Order not found with ID: " + orderId);
		}

        // Map User entity to UserDTO
        UserDTO userDTODetails = new UserDTO(
                order.getUser().getId(),
                order.getUser().getUsername(),
                order.getUser().getEmail(),
                order.getUser().getRole()
        );
        
        // Fetch the order items by orderId and map them to OrderItemDTO
        List<Order_Items> orderItemsList = orderItemsRepository.findByOrders(order);
        List<OrderItemDTO> orderItemDTOList = orderItemsList.stream()
                .map(item -> new OrderItemDTO(
                        item.getId(),
                        item.getProducts().getId(),
                        item.getProducts().getProductName(),
                        item.getProduct_qty(),
                        item.getProductPrice()
                ))
                .collect(Collectors.toList());

        // Map Orders entity to OrderDTO
        return new OrderDTO(
                order.getId(),
                userDTODetails,
                order.getTotal_amount(),
                order.getOrder_status(),
                order.getCreatedDate().toString(),
                orderItemDTOList
        );
    }

	
	
}
