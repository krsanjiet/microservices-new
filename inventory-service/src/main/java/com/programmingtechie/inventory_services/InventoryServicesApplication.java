package com.programmingtechie.inventory_services;

import com.programmingtechie.inventory_services.model.Inventory;
import com.programmingtechie.inventory_services.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServicesApplication.class, args);
	}

	@Bean //during application startup beans get created and load this method
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
			Inventory inventory1 = new Inventory();
			inventory1.setQuantity(100);
			inventory1.setSkuCode("Iphone_13");

			Inventory inventory2 = new Inventory();
			inventory2.setQuantity(0);
			inventory2.setSkuCode("Iphone_13_red");

			inventoryRepository.save(inventory1);
			inventoryRepository.save(inventory2);
		};
	}

}
