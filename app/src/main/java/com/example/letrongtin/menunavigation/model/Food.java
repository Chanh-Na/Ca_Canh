package com.example.letrongtin.menunavigation.model;

/**
 * Created by Le Trong Tin on 11/12/2017.
 */

public class Food extends BaseObject {

    private String brand;
    private String shape;
    private String weight;
    private String uses;

    public Food() {
    }

    public Food(String id, String name, int price, String status, String urlImage, String brand, String shape, String weight, String uses) {
        super(id, name, price, status, urlImage);
        this.brand = brand;
        this.shape = shape;
        this.weight = weight;
        this.uses = uses;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getUses() {
        return uses;
    }

    public void setUses(String uses) {
        this.uses = uses;
    }
}
