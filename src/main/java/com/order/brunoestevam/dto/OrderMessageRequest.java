package com.order.brunoestevam.dto;

public class OrderMessageRequest {

	public OrderMessageRequest(Long id, String status) {
		this.id = id;
		this.status = status;
	}

	private Long id;
	
	private String status;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
}
