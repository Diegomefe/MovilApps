package com.upiiz.exameniii.models;

import java.io.Serializable;

public class Producto implements Serializable {

    private int id;
    private String title;
    private String description;
    private String category;
    private double price;
    private double rating;
    private String brand;
    private String thumbnail;

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public double getRating() { return rating; }
    public String getBrand() { return brand; }
    public String getThumbnail() { return thumbnail; }
}