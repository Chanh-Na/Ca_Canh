package com.example.letrongtin.menunavigation.model;

import java.util.List;

/**
 * Created by Le Trong Tin on 11/9/2017.
 */

public class OrderDetail {
    private String id;
    private String idOrder;
    private List<Cart> carts;

    public OrderDetail() {
    }

    public OrderDetail(String id, String idOrder, List<Cart> carts) {
        this.id = id;
        this.idOrder = idOrder;
        this.carts = carts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }
}
