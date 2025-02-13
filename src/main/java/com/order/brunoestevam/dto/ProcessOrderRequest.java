package com.order.brunoestevam.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class ProcessOrderRequest {
	
	@NotNull(message = "customer id field is mandatory")
	private Long idCustomer;

	@NotNull(message = "status id field is mandatory")
	private String status;

	@Valid
	@NotNull(message = "item is mandatory") 
	private List<ProcessOrderItemRequest> items;

	public Long getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Long idCustomer) {
		this.idCustomer = idCustomer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ProcessOrderItemRequest> getItems() {
		return items;
	}

	public void setItems(List<ProcessOrderItemRequest> items) {
		this.items = items;
	}
}
