package com.programmingtechie.order_services.service;

import com.programmingtechie.order_services.dto.InventoryResponse;
import com.programmingtechie.order_services.dto.OrderLineItemsDto;
import com.programmingtechie.order_services.dto.OrderRequest;
import com.programmingtechie.order_services.event.OrderPlacedEvent;
import com.programmingtechie.order_services.model.Order;
import com.programmingtechie.order_services.model.OrderLineItems;
import com.programmingtechie.order_services.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

   public String placeOrder(OrderRequest orderRequest)
   {
       Order order = new Order();
       order.setOrderNumber(UUID.randomUUID().toString());

       List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtos()
               .stream()
               .map(this::mapToDto)
               .toList();

       order.setOrderLineItems(orderLineItemsList);

       List<String> skuCodes = order.getOrderLineItems()
                                    .stream()
                                    .map(OrderLineItems::getSkuCode)
                                    .toList();

       // Call inventory service, and place order if product is in stock
       // will pass url of inventory service
       InventoryResponse[] InventoryResponseArray = webClientBuilder.build().get()
               .uri("http://inventory-services/api/inventory",
                       uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())

               .retrieve()
               .bodyToMono(InventoryResponse[].class)  // to get response
               .block();  // this will make synchronous request, by default it was async

       System.out.println("InventoryResponseArray SIZE "+InventoryResponseArray.length + InventoryResponseArray[0].getIsInStock());

       Boolean allProductInStock = Arrays.stream(InventoryResponseArray).allMatch(i -> i.getIsInStock());


        if(allProductInStock) {
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic",new OrderPlacedEvent(order.getOrderNumber()));
            return "Order placed successfully";
        }else{
            throw new IllegalArgumentException("Product is not in stock");
        }
   }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {

       OrderLineItems orderLineItems = new OrderLineItems();
       orderLineItems.setPrice(orderLineItemsDto.getPrice());
       orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
       orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());

       return orderLineItems;
    }
}
