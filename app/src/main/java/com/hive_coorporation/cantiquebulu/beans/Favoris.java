package com.hive_coorporation.cantiquebulu.beans;

public class Favoris {

    private int id;
    private String numero;
    private String nom;
    private String corps;
    private int isFavoris;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCorps() {
        return corps;
    }

    public void setCorps(String corps) {
        this.corps = corps;
    }

    public int getIsFavoris() {
        return isFavoris;
    }

    public void setIsFavoris(int isFavoris) {
        this.isFavoris = isFavoris;
    }
}
