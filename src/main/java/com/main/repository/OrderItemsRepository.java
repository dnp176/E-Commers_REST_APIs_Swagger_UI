package com.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.main.entity.Order_Items;
import com.main.entity.Orders;

@Repository
public interface OrderItemsRepository extends JpaRepository<Order_Items, Integer> {
//	Order_Items findByOrderId(int orderId);
	List<Order_Items> findByOrders(Orders orders);
	
	@Query("SELECT o FROM Orders o JOIN FETCH o.user WHERE o.id = :orderId")
	Orders findOrderWithUser(@Param("orderId") int orderId);
}
