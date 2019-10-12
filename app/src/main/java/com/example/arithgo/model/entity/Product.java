package com.example.arithgo.model.entity;

import java.util.List;

public class Product {


    private String id;
    private String name;
    private String desc;
    private long price;

    public Product(String id, String name, String desc, long price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String toString(){
        return name+"     "+price;
    }


}
