package com.upiiz.practica_ii.db;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenciasUsuario {

    private static final String PREFS = "preferencias_usuario";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_TEMA = "tema";
    private static final String KEY_SECCION = "ultima_seccion";

    public static final String TEMA_CLARO = "claro";
    public static final String TEMA_OSCURO = "oscuro";
    public static final String SECCION_CATALOGO = "catalogo";
    public static final String SECCION_FAVORITOS = "favoritos";

    private final SharedPreferences prefs;

    public PreferenciasUsuario(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public void guardarNombre(String nombre) { prefs.edit().putString(KEY_NOMBRE, nombre).apply(); }
    public String obtenerNombre() { return prefs.getString(KEY_NOMBRE, ""); }

    public void guardarTema(String tema) { prefs.edit().putString(KEY_TEMA, tema).apply(); }
    public String obtenerTema() { return prefs.getString(KEY_TEMA, TEMA_CLARO); }

    public void guardarUltimaSeccion(String seccion) { prefs.edit().putString(KEY_SECCION, seccion).apply(); }
    public String obtenerUltimaSeccion() { return prefs.getString(KEY_SECCION, ""); }
}