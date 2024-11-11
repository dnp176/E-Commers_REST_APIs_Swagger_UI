package com.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.entity.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer>{
	Products findById(int id);
}
