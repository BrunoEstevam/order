package com.order.brunoestevam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.order.brunoestevam.config.OrderProcessorFactory;
import com.order.brunoestevam.dto.OrderStatusEnum;
import com.order.brunoestevam.dto.ProcessOrderResponse;
import com.order.brunoestevam.mapper.OrderMapper;
import com.order.brunoestevam.repository.ItemEntity;
import com.order.brunoestevam.repository.OrderEntity;
import com.order.brunoestevam.repository.OrderRepository;
import com.order.brunoestevam.service.impl.OrderProcessingService;

@ExtendWith(value = MockitoExtension.class)
public class OrderProcessingServiceTest {

	@InjectMocks
	private OrderProcessingService orderProcessingService;

	@Mock
	private OrderRepository repository;

	@Mock
	private IdempotenteService idempotenteService;

	@Mock
	private OrderProcessorFactory factory;

	@Mock
	private MessasingProducer messasingProducerService;

	@Mock
	private OrderMapper orderMapper;

	@Mock
	private OrderProcessor orderProcessor;

	private OrderEntity order;

	@BeforeEach
	public void setUp() {
		orderMapper = spy(Mappers.getMapper(OrderMapper.class));

		ItemEntity item = new ItemEntity();
		item.setQuantity(1);
		item.setPrice(new BigDecimal(10.20));

		ItemEntity item2 = new ItemEntity();
		item2.setQuantity(1);
		item2.setPrice(BigDecimal.TEN);

		order = new OrderEntity();
		order.setIdCustomer(12l);
		order.setIdempotentKey("abc");
		order.setStatus(OrderStatusEnum.CREATED.getCode());
		order.setItems(List.of(item, item2));
	}

	@Test
	@DisplayName("Deve processar o pedido")
	public void shouldProcessOrder() {
		String idempotenteKey = "test-key";

		ProcessOrderResponse response = orderMapper.mapToResponse(order);

		when(factory.getProcessor(OrderStatusEnum.CREATED.getCode())).thenReturn(orderProcessor);
		when(orderProcessor.processor(BDDMockito.any(OrderEntity.class))).thenReturn(order);

		ProcessOrderResponse result = orderProcessingService.process(order, idempotenteKey);

		verify(idempotenteService).validate(idempotenteKey);
		verify(repository).save(order);
		verify(idempotenteService).put(idempotenteKey);

		assertEquals(response.getId(), result.getId());
	}
}
