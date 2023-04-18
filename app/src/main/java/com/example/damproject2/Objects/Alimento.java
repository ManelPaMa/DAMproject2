package com.example.damproject2.Objects;

public class Alimento {

    protected String id;
    protected String nombreFood;
    protected int calories ;
    protected String typeFood;

    public Alimento(){

    }
    public Alimento(String id, String nombreFood, int calories, String typeFood){
        this.id= id;
        this.nombreFood = nombreFood;
        this.calories = calories;
        this.typeFood = typeFood;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreFood() {
        return nombreFood;
    }

    public void setNombreFood(String nombreFood) {
        this.nombreFood = nombreFood;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getTypeFood() {
        return typeFood;
    }

    public void setTypeFood(String typeFood) {
        this.typeFood = typeFood;
    }
}
