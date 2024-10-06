package com.programmetechie.product_services.repository;

import com.programmetechie.product_services.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
