package com.Inventory.Application.dsa;

import java.util.PriorityQueue;
import com.Inventory.Application.model.Product;

public class ProductHeap {

    private PriorityQueue<Product> heap =
            new PriorityQueue<>((a, b) -> a.getQuantity() - b.getQuantity());

    public void add(Product p) {
        heap.add(p);
    }

    public Product getLowStock() {
        return heap.peek();
    }
}