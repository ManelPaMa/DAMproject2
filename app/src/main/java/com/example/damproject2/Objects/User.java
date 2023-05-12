package com.example.damproject2.Objects;

import android.os.Parcel;
import android.os.Parcelable;

public class User {

    protected String email, alias, sexo, activityLvl, password;
    protected int edad;
    protected double peso, imc, altura;

    public User(){

    }
    public User(String email, String alias, String sexo, String activityLvl, String password, int edad, double peso, double imc, double altura){
        this.email = email;
        this.alias = alias;
        this.sexo = sexo;
        this.activityLvl = activityLvl;
        this.password = password;
        this.edad = edad;
        this.peso = peso;
        this.imc = imc;
        this.altura = altura;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getActivityLvl() {
        return activityLvl;
    }

    public void setActivityLvl(String activityLvl) {
        this.activityLvl = activityLvl;
    }


    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }


}
