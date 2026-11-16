package com.upiiz.dm_jdmf_08.models;

import java.util.List;

public class RespuestaClientes {
    private int estado;
    private String mensaje;
    private Datos datos;

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Datos getDatos() {
        return datos;
    }

    public void setDatos(Datos datos) {
        this.datos = datos;
    }
}
