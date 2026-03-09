package com.Inventory.Application.dsa;

import java.util.List;
import com.Inventory.Application.model.Product;

public class LinearSearch {

    public static Product search(List<Product> products, String name) {

        for (Product p : products) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }

        return null;
    }
}