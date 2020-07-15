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

    public int getIdas() {
        return idassociation;
    }

    public void setIdas(int idassociation) {
        this.idassociation = idassociation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdcat() {
        return idcategory;
    }

    public void setIdcat(int idcategory) {
        this.idcategory = idcategory;
    }
}
