package com.example.benevent.Models;

import java.util.Date;

public class User {
    int iduser ;
    String name;
    String firstname;
    Date birthdate;
    String email;
    String phone;
    String profilpicture;

    public User(String name, String firstname, String phone, String profilpicture) {
        this.name = name;
        this.firstname = firstname;
        this.phone = phone;
        this.profilpicture = profilpicture;
    }

    public User() {

    }

    public int getId() {
        return iduser;
    }

    public void setId(int iduser) {
        this.iduser = iduser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getProfilpicture() { return profilpicture; }

    public void setProfilpicture(String profilpicture) { this.profilpicture = profilpicture; }

}
