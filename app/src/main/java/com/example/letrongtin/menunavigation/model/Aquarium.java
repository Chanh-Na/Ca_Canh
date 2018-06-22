package com.example.letrongtin.menunavigation.model;

/**
 * Created by Le Trong Tin on 11/10/2017.
 */

public class Aquarium extends BaseObject {

    private String origin;
    private String size;
    private String material;
    private String color;
    private String brand;

    public Aquarium() {
    }

    public Aquarium(String id, String name, int price, String status, String urlImage, String origin, String size, String material, String color, String brand) {
        super(id, name, price, status, urlImage);
        this.origin = origin;
        this.size = size;
        this.material = material;
        this.color = color;
        this.brand = brand;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
