package com.example.benevent.Models;

public class Follow {
    int idassociation;
    int iduser;

    public Follow(int idassociation, int iduser) {
        this.idassociation = idassociation;
        this.iduser = iduser;
    }

    public int getIdas() {
        return idassociation;
    }

    public int getIdu() {
        return iduser;
    }
}
