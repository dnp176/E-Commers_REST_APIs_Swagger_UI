package com.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.entity.Orders;
import com.main.entity.Payments;
import java.util.List;


@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Integer>{
//	Payments findByOrderId(int orderId);
	Payments findByOrders(Orders orders);
}
