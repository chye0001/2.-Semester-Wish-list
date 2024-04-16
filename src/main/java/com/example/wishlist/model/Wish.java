package com.example.wishlist.model;

public class Wish {

    private long wishlistId;

    private String name;
    private long wishId;
    private String description;
    private double price;
    private String link;
    private String picture;
    private boolean isReserved;

    public Wish() {
    }

    public Wish(String name, String description, double price, String link, String picture) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.link = link;
        this.picture = picture;
        isReserved = false;
    }

    public Wish(long wishId, String name, String description, double price, String link, String picture, boolean isReserved) {
        this.wishId = wishId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.link = link;
        this.picture = picture;
        this.isReserved = isReserved;
    }

    public long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWishId() {
        return wishId;
    }

    public void setWishId(long wishId) {
        this.wishId = wishId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean isReserved) {
        this.isReserved = isReserved;
    }
}
