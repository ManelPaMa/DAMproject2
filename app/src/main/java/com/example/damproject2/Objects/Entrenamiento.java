package com.example.damproject2.Objects;

public class Entrenamiento {

    protected String fecha;
    protected String deporte;
    protected String descripcion;

    public Entrenamiento(){

    }
    public Entrenamiento(String fecha, String deporte, String descripcion){
        this.fecha= fecha;
        this.deporte= deporte;
        this.descripcion= descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
