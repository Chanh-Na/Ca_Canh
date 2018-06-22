package com.example.letrongtin.menunavigation.model;

/**
 * Created by Le Trong Tin on 11/11/2017.
 */

public class Machine extends BaseObject {

    private String capacity;
    private String origin;
    private String brand;
    private String uses;

    public Machine() {
    }

    public Machine(String id, String name, int price, String status, String urlImage, String capacity, String origin, String brand, String uses) {
        super(id, name, price, status, urlImage);
        this.capacity = capacity;
        this.origin = origin;
        this.brand = brand;
        this.uses = uses;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getUses() {
        return uses;
    }

    public void setUses(String uses) {
        this.uses = uses;
    }
}
