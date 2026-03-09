package com.Inventory.Application.dsa;

import java.util.List;
import com.Inventory.Application.model.Product;

public class BubbleSort {

    public static void sort(List<Product> products) {

        int n = products.size();

        for (int i = 0; i < n - 1; i++) {

            for (int j = 0; j < n - i - 1; j++) {

                if (products.get(j).getPrice() > products.get(j + 1).getPrice()) {

                    Product temp = products.get(j);
                    products.set(j, products.get(j + 1));
                    products.set(j + 1, temp);
                }
            }
        }
    }
}