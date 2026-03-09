package com.Inventory.Application.dsa;

import java.util.LinkedList;
import java.util.Queue;

public class OrderQueue {

    private Queue<String> queue = new LinkedList<>();

    public void enqueue(String order) {
        queue.add(order);
    }

    public String dequeue() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}