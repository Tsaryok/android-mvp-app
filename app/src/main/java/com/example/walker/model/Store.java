package com.example.walker.model;

import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public class Store {

    private String id;

    private String name;

    private String description;

    private List<String> tags;

    private GeoPoint geoPoint;

    private double rating;

    private String imageUrl;

    public Store() {
    }


    public Store(String id, String name, String description, GeoPoint geoPoint, double rating, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.geoPoint = geoPoint;
        this.rating = rating;
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

    public List<String> getTags() {
        return tags;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public double getRating() {
        return rating;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
