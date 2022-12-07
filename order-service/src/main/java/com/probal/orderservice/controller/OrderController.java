package com.probal.orderservice.controller;

import com.probal.orderservice.dto.request.OrderRequest;
import com.probal.orderservice.dto.response.Response;
import com.probal.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    public Response<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.placeOrder(orderRequest);
    }

    public Response<String> fallbackMethod(OrderRequest orderRequest, RuntimeException exception) {
        Response<String> response = new Response<>();
        response.setCode(888);
        response.setResponseMessage("Please try again later");
        return response;
    }
}
