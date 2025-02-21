package com.order.brunoestevam.mapper;

import com.order.brunoestevam.dto.OrderItemRequest;
import com.order.brunoestevam.dto.OrderItemResponse;
import com.order.brunoestevam.dto.OrderRequest;
import com.order.brunoestevam.dto.OrderResponse;
import com.order.brunoestevam.repository.ItemEntity;
import com.order.brunoestevam.repository.OrderEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-20T21:54:44-0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderEntity mapToEntity(OrderRequest request) {
        if ( request == null ) {
            return null;
        }

        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setIdCustomer( request.getIdCustomer() );
        orderEntity.setItems( orderItemRequestListToItemEntityList( request.getItems() ) );

        return orderEntity;
    }

    @Override
    public OrderRequest mapToRequest(OrderEntity request) {
        if ( request == null ) {
            return null;
        }

        OrderRequest orderRequest = new OrderRequest();

        orderRequest.setIdCustomer( request.getIdCustomer() );
        orderRequest.setItems( itemEntityListToOrderItemRequestList( request.getItems() ) );

        return orderRequest;
    }

    @Override
    public OrderResponse mapToResponse(OrderEntity entity) {
        if ( entity == null ) {
            return null;
        }

        OrderResponse orderResponse = new OrderResponse();

        orderResponse.setId( entity.getId() );
        orderResponse.setIdCustomer( entity.getIdCustomer() );
        orderResponse.setStatus( entity.getStatus() );
        orderResponse.setItems( itemEntityListToOrderItemResponseList( entity.getItems() ) );

        return orderResponse;
    }

    protected ItemEntity orderItemRequestToItemEntity(OrderItemRequest orderItemRequest) {
        if ( orderItemRequest == null ) {
            return null;
        }

        ItemEntity itemEntity = new ItemEntity();

        itemEntity.setQuantity( orderItemRequest.getQuantity() );
        itemEntity.setDescription( orderItemRequest.getDescription() );
        itemEntity.setPrice( orderItemRequest.getPrice() );

        return itemEntity;
    }

    protected List<ItemEntity> orderItemRequestListToItemEntityList(List<OrderItemRequest> list) {
        if ( list == null ) {
            return null;
        }

        List<ItemEntity> list1 = new ArrayList<ItemEntity>( list.size() );
        for ( OrderItemRequest orderItemRequest : list ) {
            list1.add( orderItemRequestToItemEntity( orderItemRequest ) );
        }

        return list1;
    }

    protected OrderItemRequest itemEntityToOrderItemRequest(ItemEntity itemEntity) {
        if ( itemEntity == null ) {
            return null;
        }

        OrderItemRequest orderItemRequest = new OrderItemRequest();

        orderItemRequest.setQuantity( itemEntity.getQuantity() );
        orderItemRequest.setDescription( itemEntity.getDescription() );
        orderItemRequest.setPrice( itemEntity.getPrice() );

        return orderItemRequest;
    }

    protected List<OrderItemRequest> itemEntityListToOrderItemRequestList(List<ItemEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderItemRequest> list1 = new ArrayList<OrderItemRequest>( list.size() );
        for ( ItemEntity itemEntity : list ) {
            list1.add( itemEntityToOrderItemRequest( itemEntity ) );
        }

        return list1;
    }

    protected OrderItemResponse itemEntityToOrderItemResponse(ItemEntity itemEntity) {
        if ( itemEntity == null ) {
            return null;
        }

        OrderItemResponse orderItemResponse = new OrderItemResponse();

        orderItemResponse.setId( itemEntity.getId() );
        orderItemResponse.setQuantity( itemEntity.getQuantity() );
        orderItemResponse.setDescription( itemEntity.getDescription() );
        orderItemResponse.setPrice( itemEntity.getPrice() );

        return orderItemResponse;
    }

    protected List<OrderItemResponse> itemEntityListToOrderItemResponseList(List<ItemEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderItemResponse> list1 = new ArrayList<OrderItemResponse>( list.size() );
        for ( ItemEntity itemEntity : list ) {
            list1.add( itemEntityToOrderItemResponse( itemEntity ) );
        }

        return list1;
    }
}
