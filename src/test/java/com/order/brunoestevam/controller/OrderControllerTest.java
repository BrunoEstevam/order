package com.order.brunoestevam.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;

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
import com.order.brunoestevam.dto.OrderStatusEnum;
import com.order.brunoestevam.dto.ProcessOrderRequest;
import com.order.brunoestevam.dto.ProcessOrderResponse;
import com.order.brunoestevam.service.impl.OrderProcessingService;

@WebMvcTest(controllers = {OrderController.class})
public class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private OrderProcessingService orderProcessingService;

	private Gson gson;

	private ProcessOrderRequest request;

	@BeforeEach
	public void setUp() {
		gson = new Gson();

		request = new ProcessOrderRequest();
		request.setIdCustomer(12L);
		request.setItems(new ArrayList<>());
		request.setStatus(OrderStatusEnum.CREATED.getCode());
	}

	@Test
	@DisplayName("Deve processar e avisar que está faltando o header")
	public void shouldProcessAndReturnMissingHeader() throws Exception {
		ProcessOrderResponse response = new ProcessOrderResponse();
		response.setId(1l);
		response.setIdCustomer(12l);
		response.setItems(new ArrayList<>());
		response.setStatus(OrderStatusEnum.CREATED.getCode());
		response.setTotalPrice(BigDecimal.TEN);

		BDDMockito.when(orderProcessingService.process(BDDMockito.any(), BDDMockito.any())).thenReturn(response);

		this.mockMvc.perform(post("/order").contentType(MediaType.APPLICATION_JSON_VALUE).content(gson.toJson(request))).andDo(print()).andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Deve processar e retornar sucesso")
	public void shouldProcessAndReturnSucess() throws Exception {
		ProcessOrderResponse response = new ProcessOrderResponse();
		response.setId(1l);
		response.setIdCustomer(12l);
		response.setItems(new ArrayList<>());
		response.setStatus(OrderStatusEnum.CREATED.getCode());
		response.setTotalPrice(BigDecimal.TEN);

		BDDMockito.when(orderProcessingService.process(BDDMockito.any(), BDDMockito.any())).thenReturn(response);

		this.mockMvc.perform(post("/order").contentType(MediaType.APPLICATION_JSON_VALUE).header("Idempotency-Key", "teste").content(gson.toJson(request))).andDo(print()).andExpect(status().isOk());
	}
}
