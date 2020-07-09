package com.example.benevent.Models;

public class Category {
    int idcat;
    String name;

    public Category(int idcat, String name) {
        this.idcat = idcat;
        this.name = name;
    }

    public int getIdcat() {
        return idcat;
    }

    public void setIdcat(int idcat) {
        this.idcat = idcat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
