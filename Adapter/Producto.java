package com.pdm.panaderia.Adapter;

public class Producto {
    private int id;
    private String nombre_producto;

    public Producto(int id, String nombre_producto, double precio_producto) {
        this.id = id;
        this.nombre_producto = nombre_producto;
        this.precio_producto = precio_producto;
    }

    public Producto(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre_producto='" + nombre_producto + '\'' +
                ", precio_producto=" + precio_producto +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public double getPrecio_producto() {
        return precio_producto;
    }

    public void setPrecio_producto(double precio_producto) {
        this.precio_producto = precio_producto;
    }

    private double precio_producto;


}
