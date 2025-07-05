package com.example.order;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private static ShoppingCart instance;
    private List<CartItem> items;

    private ShoppingCart() {
        items = new ArrayList<>();
    }

    public static ShoppingCart getInstance() {
        if (instance == null) {
            instance = new ShoppingCart();
        }
        return instance;
    }

    public void addItem(String name, double price, int imageResId) {
        for (CartItem item : items) {
            if (item.getName().equals(name)) {
                item.incrementQuantity();
                return;
            }
        }
        items.add(new CartItem(name, price, imageResId));
    }
    public void addItem(CartItem item) {
        for (CartItem existingItem : items) {
            if (existingItem.getName().equals(item.getName())) {
                existingItem.incrementQuantity();
                return;
            }
        }
        items.add(item);
    }


    public List<CartItem> getItemList() {
        return items;
    }

    public void clearCart() {
        items.clear();
    }
    public void removeItem(CartItem item) {
        items.remove(item);
    }

}
