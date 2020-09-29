package com.example.walker.model;

import com.google.firebase.Timestamp;

public class Product {

    private String id;

    private String name;

    private String description;

    private int number;

    private String storeId;

    private int discount;

    private int price;

    private Timestamp date;

    private String imageUrl;


    public Product() {
    }

    public Product(String id, String name, String description, int number, String storeId,
            int discount, int price, Timestamp date, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.number = number;
        this.storeId = storeId;
        this.discount = discount;
        this.price = price;
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getNumber() {
        return number;
    }

    public String getStoreId() {
        return storeId;
    }

    public int getDiscount() {
        return discount;
    }

    public int getPrice() {
        return price;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getDiscountedPrice(){
        return price * (100 - discount)/100;
    }
}
