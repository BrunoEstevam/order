package com.order.brunoestevam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.order.brunoestevam.config.OrderProcessorFactory;
import com.order.brunoestevam.dto.OrderMessageRequest;
import com.order.brunoestevam.dto.OrderStatusEnum;
import com.order.brunoestevam.repository.ItemEntity;
import com.order.brunoestevam.repository.OrderEntity;
import com.order.brunoestevam.repository.OrderRepository;
import com.order.brunoestevam.service.impl.OrderProcessingServiceImpl;

@ExtendWith(value = MockitoExtension.class)
public class OrderProcessingServiceImplTest {

	@InjectMocks
	private OrderProcessingServiceImpl orderProcessingService;

	@Mock
	private OrderProcessorFactory factory;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private MessasingProducerService messasingProducerService;

	@Mock
	private OrderProcessorService orderProcessorService;

	private OrderEntity order;

	@BeforeEach
	public void setUp() {
		ItemEntity item = new ItemEntity();
		item.setQuantity(1);
		item.setPrice(new BigDecimal(10.20));

		ItemEntity item2 = new ItemEntity();
		item2.setQuantity(1);
		item2.setPrice(BigDecimal.TEN);

		order = new OrderEntity();
		order.setIdCustomer(12l);
		order.setIdempotentKey("abc");
		order.setItems(List.of(item, item2));
	}

	@Test
	@DisplayName("Deveria processar o pedido e enviar a mensagem")
	public void shouldProcessOrderAndSendMessage() {
		OrderMessageRequest request = new OrderMessageRequest(1l, OrderStatusEnum.PENDING.getCode());

		when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
		when(factory.getProcessor(order.getStatus())).thenReturn(orderProcessorService);

		orderProcessingService.process(request);

		verify(orderRepository, times(1)).findById(1L);
		verify(orderProcessorService, times(1)).processor(order);
		verify(messasingProducerService, times(1)).sendOrderUpdate(any());
	}
	
    @Test
    public void shouldThrowExceptionAndSetStatusError() {
		OrderMessageRequest request = new OrderMessageRequest(1l, OrderStatusEnum.PENDING.getCode());

		when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));

        orderProcessingService.process(request);

        verify(orderRepository, times(1)).findById(1L);
        assertEquals(OrderStatusEnum.ERROR.getCode(), order.getStatus());  
    }

	@Test
	@DisplayName("Deveria lançar uma exceção quando consultado por id")
	public void shouldThrowExceptioWhenFindById() {
		when(orderRepository.findById(1L)).thenReturn(java.util.Optional.empty());
		OrderMessageRequest request = new OrderMessageRequest(1l, OrderStatusEnum.PENDING.getCode());

		assertThrows(NoSuchElementException.class, () -> orderProcessingService.process(request));
	}
}
