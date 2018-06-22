package com.example.letrongtin.menunavigation.model;

/**
 * Created by Le Trong Tin on 11/10/2017.
 */

public class Decorate extends BaseObject {

    private String material;

    private String uses;

    public Decorate() {
    }

    public Decorate(String id, String name, int price, String status, String urlImage, String material, String uses) {
        super(id, name, price, status, urlImage);
        this.material = material;
        this.uses = uses;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getUses() {
        return uses;
    }

    public void setUses(String uses) {
        this.uses = uses;
    }
}
