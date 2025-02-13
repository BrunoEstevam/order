package com.order.brunoestevam.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.order.brunoestevam.dto.ProcessOrderRequest;
import com.order.brunoestevam.dto.ProcessOrderResponse;
import com.order.brunoestevam.mapper.OrderMapper;
import com.order.brunoestevam.service.impl.OrderProcessingService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/order")
public class OrderController {

	private final OrderProcessingService service;

	public OrderController(OrderProcessingService service) {
		this.service = service;
	}

	@ApiResponses(value = { @ApiResponse(description = "Cria um novo pedido", responseCode = "200"),
			@ApiResponse(description = "Caso algum dado sejá inválido", responseCode = "400") })
	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping
	public ResponseEntity<ProcessOrderResponse> process(@RequestBody @Validated ProcessOrderRequest request, @RequestHeader(name = "Idempotency-Key", required = true) String idempotenteKey) {
		ProcessOrderResponse response = service.process(OrderMapper.INSTANCE.mapToEntity(request), idempotenteKey);
		
		return ResponseEntity.ok(response);
	}

}
