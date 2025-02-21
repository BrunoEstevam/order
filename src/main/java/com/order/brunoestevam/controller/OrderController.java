package com.order.brunoestevam.controller;

import com.order.brunoestevam.dto.OrderRequest;
import com.order.brunoestevam.dto.OrderResponse;
import com.order.brunoestevam.mapper.OrderMapper;
import com.order.brunoestevam.service.impl.OrderService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

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
	public ResponseEntity<OrderResponse> findById(@PathVariable Long id) throws InterruptedException {
		OrderResponse response = OrderMapper.INSTANCE.mapToResponse(service.findById(id));

		return ResponseEntity.ok(response);
	}

}
	