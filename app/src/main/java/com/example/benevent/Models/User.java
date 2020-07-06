package com.example.benevent.Models;

import java.util.Date;

public class User {
    int idu ;
    String name;
    String firstname;
    Date age;
    String email;
    String password;
    String phone;
    String profilepicture;
    String address;
    String description;

    public User(int idu, String name, String firstname, Date age, String email, String password, String phone, String profilepicture, String address, String description) {
        this.idu = idu;
        this.name = name;
        this.firstname = firstname;
        this.age = age;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.profilepicture = profilepicture;
        this.address = address;
        this.description = description;
    }

    public User() {

    }

    public int getId() {
        return idu;
    }

    public void setId(int idu) {
        this.idu = idu;
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

    public String getProfilepicture() { return profilepicture; }

    public void setProfilepicture(String profilepicture) { this.profilepicture = profilepicture; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
