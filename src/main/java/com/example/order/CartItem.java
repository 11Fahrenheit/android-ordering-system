package com.example.order;

public class CartItem {
    private String name;
    private double price;
    private int quantity;
    private int imageResId; // 新增：图片资源 ID
    private String note;
    public void setNote(String note) { this.note = note; }
    public String getNote() { return note; }


    public CartItem(String name, double price, int imageResId) {
        this.name = name;
        this.price = price;
        this.quantity = 1;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
