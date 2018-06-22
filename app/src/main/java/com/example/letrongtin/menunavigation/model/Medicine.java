package com.example.letrongtin.menunavigation.model;

/**
 * Created by Le Trong Tin on 11/11/2017.
 */

public class Medicine extends BaseObject {

    private String origin;
    private String weight;
    private String uses;

    public Medicine() {
    }

    public Medicine(String id, String name, int price, String status, String urlImage, String origin, String weight, String uses) {
        super(id, name, price, status, urlImage);
        this.origin = origin;
        this.weight = weight;
        this.uses = uses;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
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
