package com.example.letrongtin.menunavigation.model;

/**
 * Created by Le Trong Tin on 12/1/2017.
 */

public class Advertisement {
    private String id;
    private String urlImage;

    public Advertisement() {
    }

    public Advertisement(String id, String urlImage) {
        this.id = id;
        this.urlImage = urlImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
