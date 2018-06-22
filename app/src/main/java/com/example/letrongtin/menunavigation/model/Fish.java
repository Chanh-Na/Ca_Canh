package com.example.letrongtin.menunavigation.model;

/**
 * Created by Le Trong Tin on 11/7/2017.
 */

public class Fish extends BaseObject{

    private String origin;
    private String size;
    private String temperature;
    private String ph;
    private String relation;
    private String care;
    private String food;

    public Fish() {
    }

    public Fish(String id, String name, int price, String status, String urlImage, String origin, String size, String temperature, String ph, String relation, String care, String food) {
        super(id, name, price, status, urlImage);
        this.origin = origin;
        this.size = size;
        this.temperature = temperature;
        this.ph = ph;
        this.relation = relation;
        this.care = care;
        this.food = food;
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

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getCare() {
        return care;
    }

    public void setCare(String care) {
        this.care = care;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }
}
