package com.example.benevent.Models;

public class Association {
    int idassociation;
    String name;
    String logo;
    String acronym;
    String email;
    String phone;
    String website;
    String support;
    String password;
    int idcategory;

    public Association(int idassociation, String name, String logo, String acronym, String email, String phone, String website, String support, String password, int idcategory) {
        this.idassociation = idassociation;
        this.name = name;
        this.logo = logo;
        this.acronym = acronym;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.support = support;
        this.password = password;
        this.idcategory = idcategory;
    }

    public String getLogo() {
        return logo;
    }

    public int getIdas() {
        return idassociation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public String getSupport() {
        return support;
    }

    public int getIdcat() {
        return idcategory;
    }
}
