package com.order.brunoestevam.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.order.brunoestevam.dto.OrderRequest;
import com.order.brunoestevam.dto.OrderResponse;
import com.order.brunoestevam.dto.OrderStatusEnum;
import com.order.brunoestevam.repository.OrderEntity;
import com.order.brunoestevam.service.impl.OrderService;

@WebMvcTest(controllers = {OrderController.class})
public class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private OrderService orderProcessingService;

	private Gson gson;

	private OrderRequest request;

	@BeforeEach
	public void setUp() {
		gson = new Gson();

		request = new OrderRequest();
		request.setIdCustomer(12L);
		request.setItems(new ArrayList<>());
	}

	@Test
	@DisplayName("Deve processar e avisar que está faltando o header")
	public void shouldProcessAndReturnMissingHeader() throws Exception {
		OrderResponse response = new OrderResponse();
		response.setId(1l);
		response.setIdCustomer(12l);
		response.setItems(new ArrayList<>());
		response.setStatus(OrderStatusEnum.CREATED.getCode());

		BDDMockito.when(orderProcessingService.save(BDDMockito.any(), BDDMockito.any())).thenReturn(response);

		this.mockMvc.perform(post("/order").contentType(MediaType.APPLICATION_JSON_VALUE).content(gson.toJson(request))).andDo(print()).andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Deve processar e retornar sucesso")
	public void shouldProcessAndReturnSucess() throws Exception {
		OrderResponse response = new OrderResponse();
		response.setId(1l);
		response.setIdCustomer(12l);
		response.setItems(new ArrayList<>());
		response.setStatus(OrderStatusEnum.CREATED.getCode());

		BDDMockito.when(orderProcessingService.save(BDDMockito.any(), BDDMockito.any())).thenReturn(response);

		this.mockMvc.perform(post("/order").contentType(MediaType.APPLICATION_JSON_VALUE).header("Idempotency-Key", "teste").content(gson.toJson(request))).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Deve consulta por id com sucesso")
	public void shouldFindByIdWithSucess() throws Exception {
		OrderEntity response = new OrderEntity();
		response.setId(1l);
		response.setIdCustomer(12l);
		response.setItems(new ArrayList<>());
		response.setStatus(OrderStatusEnum.CREATED.getCode());
		
		BDDMockito.when(orderProcessingService.findById(1L)).thenReturn(response);

		this.mockMvc.perform(get("/order/1")).andDo(print()).andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Deve consulta por id e retornar nao encontrado")
	public void shouldFindByIdAndReturnNotFoun() throws Exception {
		OrderEntity response = new OrderEntity();
		response.setId(1l);
		response.setIdCustomer(12l);
		response.setItems(new ArrayList<>());
		response.setStatus(OrderStatusEnum.CREATED.getCode());
		
		BDDMockito.when(orderProcessingService.findById(1L)).thenThrow(NoSuchElementException.class);

		this.mockMvc.perform(get("/order/1")).andDo(print()).andExpect(status().isNotFound());
	}
}
