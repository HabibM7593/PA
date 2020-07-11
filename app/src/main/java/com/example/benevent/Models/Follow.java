package com.example.benevent.Models;

public class Follow {
    int idas;
    int idu;

    public Follow(int idas, int idu) {
        this.idas = idas;
        this.idu = idu;
    }

    public int getIdas() {
        return idas;
    }

    public void setIdas(int idas) {
        this.idas = idas;
    }

    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }
}
