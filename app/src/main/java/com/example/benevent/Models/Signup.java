package com.example.benevent.Models;

public class Signup {
    String name;
    String firstname;
    String birthdate;
    String email;
    String password;
    String phone;
    String profilpicture;

    public Signup() {

    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfilpicture() {
        return profilpicture;
    }

    public void setProfilpicture(String profilpicture) {
        this.profilpicture = profilpicture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}