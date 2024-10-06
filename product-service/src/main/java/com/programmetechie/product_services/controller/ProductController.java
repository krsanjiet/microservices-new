package com.programmetechie.product_services.controller;

import com.programmetechie.product_services.dto.ProductRequest;
import com.programmetechie.product_services.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.programmetechie.product_services.service.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    //injecting product service class
    private final ProductService productservice;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest){
        System.out.println("CALL REACHED **************");
        productservice.createProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        System.out.println("CALL REACHED **************");
        return productservice.getAllProducts();
    }
}
