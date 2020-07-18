package com.example.benevent.Models;

public class Category {
    int idcategory;
    String name;

    public Category(int idcategory, String name) {
        this.idcategory = idcategory;
        this.name = name;
    }

    public int getIdcat() {
        return idcategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
