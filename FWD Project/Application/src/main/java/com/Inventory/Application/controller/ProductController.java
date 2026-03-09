package com.Inventory.Application.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500", "http://localhost:*"})
public class ProductController {
    
    // ✅ STATIC LIST - Persists across ALL requests!
    private static List<Map<String, Object>> products = new ArrayList<>();
    
    // ✅ Simple init method - called by GET first time
    private void ensureInitialized() {
        if (products.isEmpty()) {
            products.add(Map.of("id", 1L, "name", "Laptop", "price", 50000.0, "date", "Today"));
            products.add(Map.of("id", 2L, "name", "Mouse", "price", 500.0, "date", "Today"));
            products.add(Map.of("id", 3L, "name", "Keyboard", "price", 1500.0, "date", "Today"));
            System.out.println("🟢 Initialized 3 products");
        }
    }
    
    @GetMapping("/all")
    public List<Map<String, Object>> getAllProducts() {
        ensureInitialized();  // ✅ Auto-init if empty
        System.out.println("📡 GET All: " + products.size() + " products");
        return products;
    }
    
    @PostMapping  
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody Map<String, Object> productData) {
        Map<String, Object> product = new HashMap<>(productData);
        product.put("id", System.currentTimeMillis() % 100000);
        product.put("date", "Today");
        products.add(product);  // ✅ SAVED FOREVER!
        
        System.out.println("🟢 NEW Product: " + product + " | Total: " + products.size());
        return ResponseEntity.ok(product);
    }
}
