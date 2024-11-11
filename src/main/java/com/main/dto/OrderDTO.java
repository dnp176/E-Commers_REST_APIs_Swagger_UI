package com.main.dto;
import java.util.List;

public class OrderDTO {
    private int id;
    private UserDTO user;
    private int totalAmount;
    private String orderStatus;
    private String createdDate;
    private List<OrderItemDTO> orderItems; // List of order items

    // Constructor
    public OrderDTO(int id, UserDTO user,int totalAmount, String orderStatus, String createdDate, List<OrderItemDTO> orderItems) {
        this.id = id;
        this.user = user;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.createdDate = createdDate;
        this.orderItems = orderItems;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public List<OrderItemDTO> getOrderItems() {
		return orderItems;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public void setOrderItems(List<OrderItemDTO> orderItems) {
		this.orderItems = orderItems;
	}

    
}
