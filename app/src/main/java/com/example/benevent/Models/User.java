package com.example.benevent.Models;

import java.util.Date;

public class User {
    int iduser ;
    String name;
    String firstname;
    Date age;
    String email;
    String password;
    String phone;
    String profilpicture;

    public User(int iduser, String name, String firstname, Date age, String email, String password, String phone, String profilpicture) {
        this.iduser = iduser;
        this.name = name;
        this.firstname = firstname;
        this.age = age;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.profilpicture = profilpicture;
    }

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

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Date getAge() {
        return age;
    }

    public void setAge(Date age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfilpicture() { return profilpicture; }

    public void setProfilpicture(String profilpicture) { this.profilpicture = profilpicture; }

}
