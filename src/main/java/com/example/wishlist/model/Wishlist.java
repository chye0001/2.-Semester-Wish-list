package com.example.wishlist.model;

import java.util.List;

public class Wishlist {

    private long wishlistId;
    private String name;
    private String picture;
    private boolean isPublic;
    private List<Wish> wishes;

    public Wishlist() {}

    public Wishlist(long wishlistId, String name, String picture, boolean isPublic, List<Wish> wishes) {
        this.wishlistId = wishlistId;
        this.name = name;
        this.picture = picture;
        this.isPublic = isPublic;
        this.wishes = wishes;
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

    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<Wish> getWishes() {
        return wishes;
    }
    public void setWishes(List<Wish> wishes) {
        this.wishes = wishes;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
