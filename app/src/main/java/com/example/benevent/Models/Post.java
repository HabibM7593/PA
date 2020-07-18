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

    public String getPictureprofilasso() {
        return pictureprofilasso;
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

    public String getAssoacro() {
        return assoacro;
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

    public int getIdev() {
        return idevent;
    }

    public void setIdev(int idevent) {
        this.idevent = idevent;
    }
}
