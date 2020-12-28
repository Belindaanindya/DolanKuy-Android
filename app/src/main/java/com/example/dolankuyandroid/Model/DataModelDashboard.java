package com.example.dolankuyandroid.Model;

import com.google.gson.annotations.SerializedName;

public class DataModelDashboard {

@SerializedName("id")
private int id;

@SerializedName("category_id")
private int category_id;

@SerializedName("name")
private String name;

@SerializedName("address")
private String address;

@SerializedName("description")
private String description;

@SerializedName("image")
private String image;

@SerializedName("contact")
private String contact;

@SerializedName("latitude")
private double latitude;

@SerializedName("longitude")
private double longitude;

@SerializedName("distance")
private double distance;



    public int getId() {
        return id;
    }

    public void setId(int userId) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}