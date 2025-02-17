package com.order.brunoestevam.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderItemRequest {
	
	@Min(value = 1, message = "quantity must be greater than 0")
	@NotNull(message = "quantity is mandatory")
	private Integer quantity;

	private String description;

	@Positive(message = "The total value must be greater than zero")
	@NotNull(message = "price is mandatory")
	private BigDecimal price;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
