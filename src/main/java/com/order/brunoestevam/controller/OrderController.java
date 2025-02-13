package com.order.brunoestevam.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.brunoestevam.dto.ProcessOrderRequest;
import com.order.brunoestevam.dto.ProcessOrderResponse;
import com.order.brunoestevam.mapper.OrderMapper;
import com.order.brunoestevam.repository.OrderEntity;
import com.order.brunoestevam.service.impl.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	private final OrderService service;

	public OrderController(OrderService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<ProcessOrderResponse> process(@RequestBody @Validated ProcessOrderRequest request, @RequestHeader(name = "Idempotency-Key", required = true) String idempotenteKey) {
		OrderEntity order = service.process(OrderMapper.INSTANCE.mapToEntity(request), idempotenteKey);
		
		return ResponseEntity.ok(OrderMapper.INSTANCE.mapToResponse(order));
	}

}
