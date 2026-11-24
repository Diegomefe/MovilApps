package com.upiiz.dm_jdmf_08.models;

public class PeticionClientes {
    private int id;
    private String nombre;
    private String correo;
    private String telefono;
    private String direccion;
    private Boolean registrado;

    public PeticionClientes(int id, String nombre, String correo, String telefono, String direccion, Boolean registrado) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.registrado = registrado;
    }

    public PeticionClientes(String nombre, String correo, String telefono, String direccion, Boolean registrado) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.registrado = registrado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Boolean getRegistrado() {
        return registrado;
    }

    public void setRegistrado(Boolean registrado) {
        this.registrado = registrado;
    }
}

// Constructor donde no tenga ID de parametro
