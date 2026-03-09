package com.Inventory.Application.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:8080")  // ← FIXED: Specific origin, no *
public class ProductController {

    @GetMapping("/all")
    public List<Map<String, Object>> getAllProducts() {
        List<Map<String, Object>> products = new ArrayList<>();
        products.add(Map.of("id", 1, "name", "Laptop", "price", 50000, "stock", 10));
        products.add(Map.of("id", 2, "name", "Mouse", "price", 500, "stock", 50));
        products.add(Map.of("id", 3, "name", "Keyboard", "price", 1500, "stock", 30));
        return products;
    }
}
