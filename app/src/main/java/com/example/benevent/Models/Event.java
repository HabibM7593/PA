package com.example.benevent.Models;

import android.util.Log;

import java.util.Date;

public class Event {
    int idev;
    String name;
    String description;
    Date dateDeb;
    Date dateFin;
    String location;
    int maxBenevole;
    String info;
    int idcat;
    int idas;
    int fakeevent;

    public Event(String name) {
        this.name = name;
    }

    public Event(int idev, String name, String description, Date dateDeb, Date dateFin, String location, int maxBenevole, String info, int idcat, int idas, int fakeevent) {
        this.idev = idev;
        this.name = name;
        this.description = description;
        this.dateDeb = dateDeb;
        this.dateFin = dateFin;
        this.location = location;
        this.maxBenevole = maxBenevole;
        this.info = info;
        this.idcat = idcat;
        this.idas = idas;
        this.fakeevent = fakeevent;
    }

    public Event() {

    }

    public int getFakeevent() {
        return fakeevent;
    }

    public void setFakeevent(int fakeevent) {
        this.fakeevent = fakeevent;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getMaxBenevole() {
        return maxBenevole;
    }

    public void setMaxBenevole(int maxBenevole) {
        this.maxBenevole = maxBenevole;
    }



    public int getIdev() {
        return idev;
    }

    public void setIdev(int idev) {
        this.idev = idev;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateDeb() {
        return dateDeb;
    }

    public void setDateDeb(Date dateDeb) {
        this.dateDeb = dateDeb;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin() {
        this.dateFin = dateFin;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMaxbenevole() { return maxBenevole; }

    public void setMaxbenevole(int maxbenevole) {
        this.maxBenevole = maxBenevole;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getIdcat() {
        return idcat;
    }

    public void setIdcat(int idcat) {
        this.idcat = idcat;
    }

    public int getIdas() {
        return idas;
    }

    public void setIdas(int idas) {
        this.idas = idas;
    }

}
