package com.probal.orderservice.service;

import com.probal.orderservice.dto.request.OrderLineItemsDto;
import com.probal.orderservice.dto.request.OrderRequest;
import com.probal.orderservice.dto.response.InventoryResponse;
import com.probal.orderservice.model.Order;
import com.probal.orderservice.model.OrderLineItems;
import com.probal.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtos()
                .stream()
                .map(this::mapFromDto).toList();
        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodes = order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        // communicate with inventory service
        InventoryResponse[] inventoryResponses = webClient.get()
                .uri("http://localhost:8003/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        assert inventoryResponses != null;
        boolean allProductsIsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if (Boolean.TRUE.equals(allProductsIsInStock)) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Not Available");
        }
        log.info("ORDER WITH NUMBER [ {} ] HAS BEEN PLACED", order.getOrderNumber());
    }

    private OrderLineItems mapFromDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());

        return orderLineItems;
    }
}
