package com.main.entity;

import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Payments {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne	
	@JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
	private Orders orders;
	
	@Column(name = "payment_method")
	@NotBlank(message = "Payment method is mandatory")
	private String paymentMethod;
	
	@Column(name = "paymentStatus")
	@NotBlank(message = "Payment status is mandatory")
	private String paymentStatus;
	
	@Column(name = "payment_date")
	private ZonedDateTime paymentDate;
	
	@PrePersist
    protected void onCreate() {
        this.paymentDate = ZonedDateTime.now();
    }

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

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public ZonedDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(ZonedDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Payments(int id, Orders orders, @NotBlank(message = "Payment method is mandatory") String paymentMethod,
			@NotBlank(message = "Payment status is mandatory") String paymentStatus, ZonedDateTime paymentDate) {
		super();
		this.id = id;
		this.orders = orders;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.paymentDate = paymentDate;
	}

	public Payments() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
