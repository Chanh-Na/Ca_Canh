package com.example.letrongtin.menunavigation.model;

/**
 * Created by Le Trong Tin on 11/9/2017.
 */

public class Order {
    private String id;
    private String nameCustomer;
    private String email;
    private String phonenumber;

    public Order() {
    }

    public Order(String id, String nameCustomer, String email, String phonenumber) {
        this.id = id;
        this.nameCustomer = nameCustomer;
        this.email = email;
        this.phonenumber = phonenumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
