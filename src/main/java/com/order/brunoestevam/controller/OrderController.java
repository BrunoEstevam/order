package com.order.brunoestevam.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.order.brunoestevam.dto.OrderRequest;
import com.order.brunoestevam.dto.OrderResponse;
import com.order.brunoestevam.mapper.OrderMapper;
import com.order.brunoestevam.service.impl.OrderService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/order")
public class OrderController {

	private final OrderService service;

	public OrderController(OrderService service) {
		this.service = service;
	}

	@ApiResponses(value = { @ApiResponse(description = "Cria um novo pedido", responseCode = "200"),
			@ApiResponse(description = "Caso algum dado sejá inválido", responseCode = "400") })
	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping
	public ResponseEntity<OrderResponse> save(@RequestBody @Validated OrderRequest request, @RequestHeader(name = "Idempotency-Key", required = true) String idempotenteKey) {
		OrderResponse response = service.save(OrderMapper.INSTANCE.mapToEntity(request), idempotenteKey);
		
		return ResponseEntity.ok(response);
	}
	
	
	@ApiResponses(value = { @ApiResponse(description = "Consulta pedidos por id", responseCode = "200"),
			@ApiResponse(description = "Caso não encontre", responseCode = "404") })
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/{id}")
	public ResponseEntity<OrderResponse> findById(@PathVariable Long id) {
		OrderResponse response = OrderMapper.INSTANCE.mapToResponse(service.findById(id));
		 
		return ResponseEntity.ok(response);
	}

}
	