package com.order.brunoestevam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.order.brunoestevam.dto.OrderStatusEnum;
import com.order.brunoestevam.repository.OrderEntity;
import com.order.brunoestevam.service.impl.CreateOrderServiceImpl;

@ExtendWith(value = MockitoExtension.class)
public class CreateOrderServiceImplTest {

	@InjectMocks
	private CreateOrderServiceImpl createOrderServiceImpl;
	
	@Mock
	private CalculateOrderPriceService calculateOrderPrice;
	
	@Test
	@DisplayName("Deve criar o pedido com status de criado")
	public void shouldCreateOrderWithStatusOfCreated() {
		BDDMockito.when(calculateOrderPrice.calculate(anyList())).thenReturn(BigDecimal.TEN);
		
		OrderEntity order = new OrderEntity();
		order.setIdCustomer(12l);
		order.setIdempotentKey("abc");
		order.setItems(new ArrayList<>());
		
		OrderEntity orderResponse = createOrderServiceImpl.processor(order);
		
		assertEquals(orderResponse.getTotalPrice(), BigDecimal.TEN);
		assertEquals(OrderStatusEnum.CREATED.getCode(), orderResponse.getStatus());
	}
	
	@Test
	@DisplayName("Deve retornar verdadeiro quando o status for pendente")
	public void shouldReturnTrueWhenStatusIsPending() {
		assertEquals(createOrderServiceImpl.isStatus(OrderStatusEnum.PENDING.getCode()), true);
	}

	@Test
	@DisplayName("Deve retornar falso quando o status for diferente de pendente")
	public void shouldReturnFlaseWhenStatusDiferenteFromPending() {
		assertEquals(createOrderServiceImpl.isStatus(OrderStatusEnum.COMPLETED.getCode()), false);
	}
	
	@Test
	@DisplayName("Deve retornar falso quando o status for nulo")
	public void shouldReturnFalseWhenStatusIsNull() {
		assertEquals(createOrderServiceImpl.isStatus(null), false);
	}
}
