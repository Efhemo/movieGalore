package com.example.efhemo.platingapp.Model;

public class VidModel {


    private String key;

    private String name;

    public VidModel() {
    }

    public VidModel(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }
}
