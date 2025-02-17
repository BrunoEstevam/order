package com.order.brunoestevam.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.order.brunoestevam.dto.OrderMessageRequest;
import com.order.brunoestevam.dto.OrderResponse;
import com.order.brunoestevam.dto.OrderStatusEnum;
import com.order.brunoestevam.repository.ItemEntity;
import com.order.brunoestevam.repository.OrderEntity;
import com.order.brunoestevam.repository.OrderRepository;
import com.order.brunoestevam.service.impl.IdempotenteService;
import com.order.brunoestevam.service.impl.OrderService;

@ExtendWith(value = MockitoExtension.class)
public class OrderServiceImplTest {

	@InjectMocks
	private OrderService orderService;

	@Mock
	private OrderRepository repository;

	@Mock
	private IdempotenteService idempotenteService;

	@Mock
	private MessasingProducerService messasingProducerService;
	
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
	@DisplayName("Deveria salvar e enviar a mensagem")
	public void shouldSaveOrderAndSendMessage() {
		String idempotenteKey = "unique-key";

		when(repository.save(any(OrderEntity.class))).thenReturn(this.order);

		OrderResponse response = orderService.save(order, idempotenteKey);

		verify(repository, times(1)).save(order);
		verify(messasingProducerService, times(1)).sendOrder(any(OrderMessageRequest.class));

		assertNotNull(response);
		assertEquals(order.getId(), response.getId());
		assertEquals(OrderStatusEnum.PENDING.getCode(), response.getStatus());
	}

	@Test
	@DisplayName("Deveria retornar o pedido")
	public void shouldReturnOrder() {
		OrderEntity order = new OrderEntity();
		order.setId(1L);

		when(repository.findById(1L)).thenReturn(java.util.Optional.of(order));

		OrderEntity orderResponse = orderService.findById(1L);

		assertNotNull(orderResponse);
		assertEquals(1L, orderResponse.getId());
	}

	@Test
	@DisplayName("Deveria lançar uma exceçãi quando nao encontrado")
	public void shouldThrowExceptionWhenNotFound() {
		when(repository.findById(1L)).thenReturn(java.util.Optional.empty());

		assertThrows(NoSuchElementException.class, () -> orderService.findById(1L));
	}

}
