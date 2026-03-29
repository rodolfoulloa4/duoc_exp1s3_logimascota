package com.rulloa.s3c.logimascota.model;

public class Producto {
    private String id;
    private String nombre;
    private double precio;
    private double peso;

    public Producto(String id, String nombre, double precio, double peso) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.peso = peso;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

}
