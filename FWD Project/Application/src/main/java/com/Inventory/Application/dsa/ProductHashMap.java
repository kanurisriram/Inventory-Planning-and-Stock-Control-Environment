package com.Inventory.Application.dsa;

import java.util.HashMap;
import com.Inventory.Application.model.Product;

public class ProductHashMap {

    private HashMap<Integer, Product> map = new HashMap<>();

    public void addProduct(Product product) {
        map.put(product.getId(), product);
    }

    public Product getProduct(int id) {
        return map.get(id);
    }

    public void removeProduct(int id) {
        map.remove(id);
    }
}