package com.example.benevent.Models;

public class Feedback {
    int iduser;
    String title;
    String content;
    String date;
    String status;
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

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIdu() {
        return iduser;
    }

    public void setIdu(int iduser) {
        this.iduser = iduser;
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
        return idtype;
    }

    public void setIdty(int idtype) {
        this.idtype = idtype;
    }
}
