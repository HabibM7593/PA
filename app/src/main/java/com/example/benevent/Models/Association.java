package com.example.benevent.Models;

import java.util.Date;

public class Association {
    int idas;
    String name;
    String logo;
    String acronym;
    String email;
    String phone;
    String website;
    String support;
    String password;
    int idcat;

    public Association(int idas, String name, String logo, String acronym, String email, String phone, String website, String support, String password, int idcat) {
        this.idas = idas;
        this.name = name;
        this.logo = logo;
        this.acronym = acronym;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.support = support;
        this.password = password;
        this.idcat = idcat;
    }

    public int getIdas() {
        return idas;
    }

    public void setIdas(int idas) {
        this.idas = idas;
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
        return idcat;
    }

    public void setIdcat(int idcat) {
        this.idcat = idcat;
    }
}
