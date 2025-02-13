package com.order.brunoestevam.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProcessOrderResponse {

	private Long id;

	private Long idCustomer;

	private String status;

	private BigDecimal totalPrice;

	private List<ProcessOrderItemResponse> items;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

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

	public List<ProcessOrderItemResponse> getItems() {
		return items;
	}

	public void setItems(List<ProcessOrderItemResponse> items) {
		this.items = items;
	}
}
