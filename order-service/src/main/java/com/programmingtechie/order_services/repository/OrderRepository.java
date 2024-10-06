package com.programmingtechie.order_services.repository;

import com.programmingtechie.order_services.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
