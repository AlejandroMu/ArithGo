package com.example.arithgo.model.entity;

import com.example.arithgo.model.data.EstudentRepository;

import java.util.List;

public class Student {

    private String id;
    private String name;
    private long points;
    private List<Product> products;

    public Student(String id, String name, long points) {
        this.id = id;
        this.name = name;
        this.points = points;
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

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String toString(){
        return  name+"\n Puntos:  "+points;
    }

    public boolean addProduct(Product p) {
        if(p.getPrice()<=points){
            points-=p.getPrice();
            EstudentRepository.updatePoint(this);
            return true;
        }
        return false;
    }
}
