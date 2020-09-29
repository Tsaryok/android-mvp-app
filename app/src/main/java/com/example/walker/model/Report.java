package com.example.walker.model;

import java.util.List;

public class Report {

    private String id;

    private User user;

    private String userId;

    private String storeId;

    private int rating;

    private String text;

    private List<String> imageUrls;

    public Report(String id, String userId, String storeId, String text, int rating, List<String> imageUrls) {
        this.id = id;
        this.userId = userId;
        this.storeId = storeId;
        this.text = text;
        this.rating = rating;
        this.imageUrls = imageUrls;
    }

    public Report() {
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getUserId() {
        return userId;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getText() {
        return text;
    }

    public int getRating() {
        return rating;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }
}
