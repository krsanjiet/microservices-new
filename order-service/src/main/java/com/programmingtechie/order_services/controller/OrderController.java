package com.programmingtechie.order_services.controller;

import ch.qos.logback.classic.Logger;
import com.programmingtechie.order_services.dto.OrderRequest;
import com.programmingtechie.order_services.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@CircuitBreaker(name="inventory",fallbackMethod = "fallbackMethod")
@TimeLimiter(name="inventory")
@Retry(name="inventory")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest){
        System.out.println("orderRequest data "+orderRequest.toString());
        return CompletableFuture.supplyAsync(()->orderService.placeOrder(orderRequest));
    }

    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()->"Oops! Something went wrong, Please order after sometime!");
    }
}
