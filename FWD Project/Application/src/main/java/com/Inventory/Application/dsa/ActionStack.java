package com.Inventory.Application.dsa;

import java.util.Stack;

public class ActionStack {

    private Stack<String> stack = new Stack<>();

    public void push(String action) {
        stack.push(action);
    }

    public String pop() {
        if (!stack.isEmpty()) {
            return stack.pop();
        }
        return null;
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}