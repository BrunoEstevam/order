package com.order.brunoestevam.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

	@Bean
	RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	Queue queueStatusChange() {
		return new Queue("order.v1.status-change", true);
	}

	@Bean
	Queue queueProcessor() {
		return QueueBuilder.durable("order.v1.processor").withArgument("x-message-ttl", 60000)
				.withArgument("x-max-retries", 1).withArgument("x-dead-letter-exchange", "order.v1.exchange.dlx")
				.withArgument("x-dead-letter-routing-key", "processor-dlq").build();
	}

	@Bean
	Queue queueProcessorDlq() {
		return QueueBuilder.durable("order.v1.processor.dlq").build();
	}

	@Bean
	MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(jsonMessageConverter());
		return template;
	}

	@Bean
	RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(jsonMessageConverter());

		return factory;
	}

	@Bean
	DirectExchange orderExchange() {
		return new DirectExchange("order.v1.exchange", true, false);
	}

	@Bean
	DirectExchange orderExchangeDlx() {
		return new DirectExchange("order.v1.exchange.dlx", true, false);
	}

	@Bean
	Binding bindingStatusChange(Queue queueStatusChange, DirectExchange orderExchange) {
		return BindingBuilder.bind(queueStatusChange).to(orderExchange).with("status-change");
	}

	@Bean
	Binding bindingProcessorDlq(Queue queueProcessorDlq, DirectExchange orderExchangeDlx) {
		return BindingBuilder.bind(queueProcessorDlq).to(orderExchangeDlx).with("processor-dlq");
	}

	@Bean
	Binding bindingProcessor(Queue queueProcessor, DirectExchange orderExchange) {
		return BindingBuilder.bind(queueProcessor).to(orderExchange).with("processor");
	}

	@Bean
	ApplicationListener<ApplicationReadyEvent> applicationListener(RabbitAdmin rabbitAdmin) {
		return event -> rabbitAdmin.initialize();
	}
}
