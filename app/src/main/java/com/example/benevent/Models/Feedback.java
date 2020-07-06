package com.example.benevent.Models;
public class Feedback {
    String title;
    String content;
    int idty;
    String plateform;

    public Feedback(String title, String content, int idty,String plateform) {
        this.title = title;
        this.content = content;
        this.idty = idty;
        this.plateform = plateform;

    }

    public String getPlateform() {
        return plateform;
    }

    public void setPlateform(String plateform) {
        this.plateform = plateform;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIdty() {
        return idty;
    }

    public void setIdty(int idty) {
        this.idty = idty;
    }
}
