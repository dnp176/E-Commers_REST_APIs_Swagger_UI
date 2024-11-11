package com.main.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "order_items")
public class Order_Items {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private int id;
	
	@NotNull(message = "Order ID is mandatory")
    @ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
	private Orders orders;
	
	@NotNull(message = "Product ID is mandatory")
	@ManyToOne
	@JoinColumn(name = "product_id",referencedColumnName = "id", nullable = false)
	private Products products;
	
	@Column(name = "product_qty")
	private int product_qty;
	
	@Column(name = "product_price", nullable = false)
	@NotBlank(message = "Product price is mandatory")
	@Digits(integer = 10, fraction = 2, message = "Product price must be a valid decimal value with up to 2 decimal places")
	private BigDecimal productPrice;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public int getProduct_qty() {
		return product_qty;
	}

	public void setProduct_qty(int product_qty) {
		this.product_qty = product_qty;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public Order_Items(int id, @NotNull(message = "Order ID is mandatory") Orders orders,
			@NotNull(message = "Product ID is mandatory") Products products, int product_qty,
			@NotBlank(message = "Product price is mandatory") @Digits(integer = 10, fraction = 2, message = "Product price must be a valid decimal value with up to 2 decimal places") BigDecimal productPrice) {
		super();
		this.id = id;
		this.orders = orders;
		this.products = products;
		this.product_qty = product_qty;
		this.productPrice = productPrice;
	}

	public Order_Items() {
		super();
		// TODO Auto-generated constructor stub
	}
}
