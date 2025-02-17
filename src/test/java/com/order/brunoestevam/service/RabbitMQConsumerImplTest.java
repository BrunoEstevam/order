package com.order.brunoestevam.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.order.brunoestevam.dto.OrderMessageRequest;
import com.order.brunoestevam.service.impl.RabbitMQConsumerServiceImpl;

@ExtendWith(value = MockitoExtension.class)
public class RabbitMQConsumerImplTest {

	@InjectMocks
	private RabbitMQConsumerServiceImpl rabbitMQConsumerImpl;

	@Mock
	private OrderProcessingService orderProcessingService;

    @Test
    @DisplayName("Deve processar a mensagem com sucesso")
    public void shouldProcessOrderMessageRequestWithSucess() {
        OrderMessageRequest request = new OrderMessageRequest(1L, "pen");

        rabbitMQConsumerImpl.receiveMessage(request);

        verify(orderProcessingService, times(1)).process(request);  
    }
}
