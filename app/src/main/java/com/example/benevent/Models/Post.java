package com.example.benevent.Models;

public class Post {
    int idpo;
    String message;
    String date;
    int idu;
    int idas;
    int idev;
    String eventname;
    String assoacro;
    String nameuser;
    String pictureprofilasso;
    String pictureprofiluser;
    String nomprenom;

    public Post(int idpo, String message, String date, int idas, int idev, String eventname, String assoacro) {
        this.idpo = idpo;
        this.message = message;
        this.date = date;
        this.idas = idas;
        this.idev = idev;
        this.eventname = eventname;
        this.assoacro = assoacro;
    }

    public Post(int idpo, int idu, String message, String date, int idev, String eventname, String assoacro) {
        this.idpo = idpo;
        this.message = message;
        this.date = date;
        this.idu = idu;
        this.idev = idev;
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
        return idpo;
    }

    public void setIdpo(int idpo) {
        this.idpo = idpo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }

    public int getIdas() {
        return idas;
    }

    public void setIdas(int idas) {
        this.idas = idas;
    }

    public int getIdev() {
        return idev;
    }

    public void setIdev(int idev) {
        this.idev = idev;
    }
}
