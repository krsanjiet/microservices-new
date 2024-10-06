package com.programmetechie.product_services.service;

import com.programmetechie.product_services.dto.ProductRequest;
import com.programmetechie.product_services.dto.ProductResponse;
import com.programmetechie.product_services.model.Product;
import com.programmetechie.product_services.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    /*
    final added so we need constructor
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    INSTEAD OF USING THIS WE CAN USE LOMBOK REQUIREDArgConstructor annotation
    */


    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

    productRepository.save(product);

    log.info("Product is saved !!! "+product.getId());
    }

    public List<ProductResponse> getAllProducts() {

        List<Product> productList =  productRepository.findAll();

        return productList.stream().map(this::mapToProductResponse).toList();

    }

    private ProductResponse mapToProductResponse(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
