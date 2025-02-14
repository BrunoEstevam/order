package com.order.brunoestevam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;

import java.math.BigDecimal;
import java.util.ArrayList;

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
	private CalculateOrderPrice calculateOrderPrice;
	
	@Test
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
	public void shouldReturnTrueWhenStatusIsCreated() {
		assertEquals(createOrderServiceImpl.isStatus(OrderStatusEnum.CREATED.getCode()), true);
	}

	@Test
	public void shouldReturnFlaseWhenStatusDiferenteFromCreated() {
		assertEquals(createOrderServiceImpl.isStatus(OrderStatusEnum.COMPLETED.getCode()), false);
	}
	
	@Test
	public void shouldReturnFalseWhenStatusIsNull() {
		assertEquals(createOrderServiceImpl.isStatus(null), false);
	}
}
