package com.example.letrongtin.menunavigation.model;

/**
 * Created by Le Trong Tin on 11/10/2017.
 */

public class BaseObject {

    private String id;
    private String name;
    private int price;
    private String status;
    private String urlImage;

    public BaseObject() {
    }

    public BaseObject(String id, String name, int price, String status, String urlImage) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.status = status;
        this.urlImage = urlImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
