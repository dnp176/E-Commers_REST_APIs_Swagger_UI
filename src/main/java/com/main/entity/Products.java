package com.main.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Products {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "product_img")
	private String productImg;
	
	@Column(name = "product_name", unique = true, nullable = false)
	@NotBlank(message = "Product name is mandatory")
	@Size(min = 5, max = 50, message = "Product must be between 5 and 50 characters")
	private String productName;
	
	@Column(name = "product_description")
	private String productDescription;
	
	@Column(name = "product_price", nullable = false)
	@NotBlank(message = "Product price is mandatory")
	@Digits(integer = 10, fraction = 2, message = "Product price must be a valid decimal value with up to 2 decimal places")
	private BigDecimal productPrice;
	
	@Column(name = "product_stock", nullable = false)
	@NotBlank(message = "Product stock is mandatory")
	@Max(value = 1000, message = "Product stock cannot exceed 1000")
	private int productStock;
	
	@Column(name = "product_category")
	@NotBlank(message = "Product category is mandatory")
	private int productCategory;
	
	@CreationTimestamp
	@Column(name = "created_date", nullable = false, updatable = false)
	private ZonedDateTime createdDate; 
	
	@PrePersist
    protected void onCreate() {
        this.createdDate = ZonedDateTime.now();
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductImg() {
		return productImg;
	}

	public void setProductImg(String productImg) {
		this.productImg = productImg;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public int getProductStock() {
		return productStock;
	}

	public void setProductStock(int productStock) {
		this.productStock = productStock;
	}

	public int getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(int productCategory) {
		this.productCategory = productCategory;
	}

	public ZonedDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(ZonedDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public Products(int id, String productImg,
			@NotBlank(message = "Product name is mandatory") @Size(min = 5, max = 50, message = "Product must be between 5 and 50 characters") String productName,
			String productDescription,
			@NotBlank(message = "Product price is mandatory") @Digits(integer = 10, fraction = 2, message = "Product price must be a valid decimal value with up to 2 decimal places") BigDecimal productPrice,
			@NotBlank(message = "Product stock is mandatory") @Max(value = 1000, message = "Product stock cannot exceed 1000") int productStock,
			@NotBlank(message = "Product category is mandatory") int productCategory, ZonedDateTime createdDate) {
		super();
		this.id = id;
		this.productImg = productImg;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productPrice = productPrice;
		this.productStock = productStock;
		this.productCategory = productCategory;
		this.createdDate = createdDate;
	}

	public Products() {
		super();
		// TODO Auto-generated constructor stub
	}

}
