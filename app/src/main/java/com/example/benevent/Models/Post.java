package com.example.benevent.Models;

public class Post {
    int idpost;
    String message;
    String date;
    int iduser;
    int idassociation;
    int idevent;
    String eventname;
    String assoacro;
    String nameuser;
    String pictureprofilasso;
    String pictureprofiluser;
    String nomprenom;

    public Post(int idpost, String message, String date, int idassociation, int idevent, String eventname, String assoacro) {
        this.idpost = idpost;
        this.message = message;
        this.date = date;
        this.idassociation = idassociation;
        this.idevent = idevent;
        this.eventname = eventname;
        this.assoacro = assoacro;
    }

    public Post(int idpost, int iduser, String message, String date, int idevent, String eventname, String assoacro) {
        this.idpost = idpost;
        this.message = message;
        this.date = date;
        this.iduser = iduser;
        this.idevent = idevent;
        this.eventname = eventname;
        this.assoacro = assoacro;
    }

    public Post() {
    }

    public String getNomprenom() {
        return nomprenom;
    }

    public void setNomprenom(String nomprenom) {
        this.nomprenom = nomprenom;
    }

    public String getPictureprofiluser() {
        return pictureprofiluser;
    }

    public void setPictureprofiluser(String pictureprofiluser) {
        this.pictureprofiluser = pictureprofiluser;
    }

    public String getNameuser() {
        return nameuser;
    }

    public void setNameuser(String nameuser) {
        this.nameuser = nameuser;
    }

    public String getPictureprofilasso() {
        return pictureprofilasso;
    }

    public void setPictureprofilasso(String pictureprofilasso) {
        this.pictureprofilasso = pictureprofilasso;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getAssoacro() {
        return assoacro;
    }

    public void setAssoacro(String assoacro) {
        this.assoacro = assoacro;
    }

    public int getIdpo() {
        return idpost;
    }

    public void setIdpo(int idpost) {
        this.idpost = idpost;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIdu() {
        return iduser;
    }

    public void setIdu(int iduser) {
        this.iduser = iduser;
    }

    public int getIdas() {
        return idassociation;
    }

    public void setIdas(int idassociation) {
        this.idassociation = idassociation;
    }

    public int getIdev() {
        return idevent;
    }

    public void setIdev(int idevent) {
        this.idevent = idevent;
    }
}
