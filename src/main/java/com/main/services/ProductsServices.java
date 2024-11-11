package com.main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.main.entity.Products;
import com.main.repository.ProductsRepository;

@Service
public class ProductsServices {

	@Autowired
	ProductsRepository productsRepository;

	public List<Products> addProducts(Products products) {
		return (List<Products>) productsRepository.saveAndFlush(products);
	}

	public List<Products> updatedProducts(Products products) {
		return (List<Products>) productsRepository.saveAndFlush(products);
	}

	public List<Products> getAllProducts() {
		return productsRepository.findAll();
	}

	public Products getProductFindById(int product_id) {
		return productsRepository.findById(product_id);
	}

	public String removeProduct(int product_id) {
		productsRepository.deleteById(product_id);
		return "Remove Product Successfully !!!";
	}

}
