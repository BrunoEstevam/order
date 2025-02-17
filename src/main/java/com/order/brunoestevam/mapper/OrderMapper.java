package com.order.brunoestevam.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.order.brunoestevam.dto.OrderRequest;
import com.order.brunoestevam.dto.OrderResponse;
import com.order.brunoestevam.repository.OrderEntity;

@Mapper
public interface OrderMapper {

	OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

	OrderEntity mapToEntity(OrderRequest request);

	OrderRequest mapToRequest(OrderEntity request);
	
	OrderResponse mapToResponse(OrderEntity entity);
}
	