package com.Inventory.Application.dsa;

import java.util.List;
import com.Inventory.Application.model.Product;

public class BinarySearch {

    public static Product search(List<Product> products, String name) {

        int left = 0;
        int right = products.size() - 1;

        while (left <= right) {

            int mid = left + (right - left) / 2;

            Product midProduct = products.get(mid);

            int result = midProduct.getName().compareToIgnoreCase(name);

            if (result == 0) {
                return midProduct;
            }

            if (result < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return null;
    }
}