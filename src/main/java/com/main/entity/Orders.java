package com.main.entity;

import java.time.ZonedDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Orders {
	
	@Id
    @Column(unique = true)
    private int id;
	
    @NotNull(message = "User ID is mandatory")
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;

    @Column(name = "total_amount")
    @Min(value = 1, message = "Total amount must be greater than 0")
    private int total_amount;

    @Column(name = "order_status")
    @NotBlank(message = "Order status is mandatory")
    private String order_status;

    @Column(name = "created_date", nullable = false, updatable = false)
    private ZonedDateTime createdDate;
    
    @OneToMany(mappedBy = "orders")
    private List<Order_Items> orderItems;
    
    @PrePersist
    public void prePersist() {
        this.createdDate = ZonedDateTime.now();
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(int total_amount) {
		this.total_amount = total_amount;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public ZonedDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(ZonedDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public Orders(int id, @NotNull(message = "User ID is mandatory") User user,
			@Min(value = 1, message = "Total amount must be greater than 0") int total_amount,
			@NotBlank(message = "Order status is mandatory") String order_status, ZonedDateTime createdDate) {
		super();
		this.id = id;
		this.user = user;
		this.total_amount = total_amount;
		this.order_status = order_status;
		this.createdDate = createdDate;
	}

	public Orders() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
}
