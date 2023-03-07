package com.epf.rentmanager.model;

public class Vehicle {

    private int id;
    private String constructeur;
    private int nb_places;

    public Vehicle(int id, String constructeur, int nb_places) {
        this.id = id;
        this.constructeur = constructeur;
        this.nb_places = nb_places;
    }

    public Vehicle(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public int getNb_places() {
        return nb_places;
    }

    public void setNb_places(int nb_places) {
        this.nb_places = nb_places;
    }

    @Override
    public String toString() {
        return "Id = " + id + ",  Constructeur = '" + constructeur + "',  Nombre de places = " + nb_places;
    }
}
