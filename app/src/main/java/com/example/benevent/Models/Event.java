package com.example.benevent.Models;

import android.util.Log;

import java.util.Date;

public class Event {
    int idevent;
    String name;
    String description;
    Date dateDeb;
    Date dateFin;
    String location;
    int maxBenevole;
    String info;
    int idcategory;
    int idassociation;
    int fakeevent;

    public Event(String name) {
        this.name = name;
    }

    public Event(int idevent, String name, String description, Date dateDeb, Date dateFin, String location, int maxBenevole, String info, int idcategory, int idassociation, int fakeevent) {
        this.idevent = idevent;
        this.name = name;
        this.description = description;
        this.dateDeb = dateDeb;
        this.dateFin = dateFin;
        this.location = location;
        this.maxBenevole = maxBenevole;
        this.info = info;
        this.idcategory = idcategory;
        this.idassociation = idassociation;
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
        return idevent;
    }

    public void setIdev(int idevent) {
        this.idevent = idevent;
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
        return idcategory;
    }

    public void setIdcat(int idcategory) {
        this.idcategory = idcategory;
    }

    public int getIdas() {
        return idassociation;
    }

    public void setIdas(int idassociation) {
        this.idassociation = idassociation;
    }

    @Override
    public String toString() {
        return name;
    }

}
