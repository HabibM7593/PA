package com.example.benevent.Models;

public class Signup {
    String name;
    String firstname;
    String age;
    String email;
    String password;
    String phone;
    String profilpicture;

    public Signup(String name, String firstname, String age, String email, String password, String phone, String profilpicture) {
        this.name = name;
        this.firstname = firstname;
        this.age = age;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.profilpicture = profilpicture;
    }

    public Signup() {

    }

    public String getPhone() {
        return phone;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
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
}