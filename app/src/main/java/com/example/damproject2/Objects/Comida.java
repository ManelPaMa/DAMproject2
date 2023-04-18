package com.example.damproject2.Objects;

import java.util.ArrayList;

public class Comida {

    protected String type;
    protected ArrayList<Alimento> contenidoComida;

    public Comida() {
    }
    public Comida(String type, ArrayList<Alimento> contenidoComida){
        this.type = type;
        this.contenidoComida = contenidoComida;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Alimento> getContenidoComida() {
        return contenidoComida;
    }

    public void setContenidoComida(ArrayList<Alimento> contenidoComida) {
        this.contenidoComida = contenidoComida;
    }
}
