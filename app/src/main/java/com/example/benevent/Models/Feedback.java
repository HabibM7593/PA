package com.example.benevent.Models;

public class Feedback {
    int iduser;
    String title;
    String content;
    String date;
    int note;
    int idtype;
    String plateform;

    public Feedback(int iduser, String title, String content, String date, int idtype, String plateform) {
        this.iduser = iduser;
        this.title = title;
        this.content = content;
        this.date = date;
        this.idtype = idtype;
        this.plateform = plateform;
    }

    public Feedback(int iduser, String content, String date, int note, int idtype, String plateform) {
        this.iduser = iduser;
        this.content = content;
        this.date = date;
        this.note = note;
        this.idtype = idtype;
        this.plateform = plateform;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
