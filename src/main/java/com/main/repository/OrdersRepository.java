package com.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.entity.Orders;
import com.main.entity.Products;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
	Orders findById(int id);
}
