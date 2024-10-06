package com.programmingtechie.inventory_services.controller;

import com.programmingtechie.inventory_services.dto.InventoryResponse;
import com.programmingtechie.inventory_services.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    @Autowired
    private final InventoryService inventoryService;

    // path variable for single variable
    // requestparam is for multiple variable ?sku-code=iphone_13&sku-code=iphone_13pro

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isStock(@RequestParam List<String> skuCode) {
        return inventoryService.isInStock(skuCode);

    }
}
