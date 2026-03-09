package com.Inventory.Application.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Inventory.Application.model.Product;
import com.Inventory.Application.repository.ProductRepository;
import com.Inventory.Application.dsa.LinearSearch;
import com.Inventory.Application.dsa.BubbleSort;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product addProduct(Product product) {
        return repository.addProduct(product);
    }

    public List<Product> getAllProducts() {
        return repository.getAllProducts();
    }

    public Product getProductById(int id) {
        return repository.findById(id);
    }

    public void deleteProduct(int id) {
        repository.deleteProduct(id);
    }

    public Product searchProduct(String name) {
        List<Product> products = repository.getAllProducts();
        return LinearSearch.search(products, name);
    }

    public List<Product> sortProducts() {
        List<Product> products = repository.getAllProducts();
        BubbleSort.sort(products);
        return products;
    }
}