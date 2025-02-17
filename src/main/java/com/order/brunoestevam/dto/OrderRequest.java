package com.order.brunoestevam.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class OrderRequest {
	
	@NotNull(message = "customer id field is mandatory")
	private Long idCustomer;

	@Valid
	@NotNull(message = "item is mandatory") 
	private List<OrderItemRequest> items;

	public Long getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Long idCustomer) {
		this.idCustomer = idCustomer;
	}

	public List<OrderItemRequest> getItems() {
		return items;
	}

	public void setItems(List<OrderItemRequest> items) {
		this.items = items;
	}
}
