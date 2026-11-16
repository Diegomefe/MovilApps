package com.upiiz.practica_ii.db;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.upiiz.practica_ii.models.Libro;

@Entity(tableName = "libros")
public class LibroEntity {
    @PrimaryKey
    public int id;
    public String titulo;
    public String autor;
    public String editorial;
    public int paginas;
    public boolean disponible;

    public LibroEntity(int id, String titulo, String autor, String editorial, int paginas, boolean disponible) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.paginas = paginas;
        this.disponible = disponible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public static LibroEntity fromLibro(Libro l) {
        String titulo = l.getTitulo() != null ? l.getTitulo() : "";
        return new LibroEntity(l.getId(), titulo, l.getAutor(), l.getEditorial(), l.getPaginas(), l.isDisponible());
    }

    public Libro toLibro() {
        return new Libro(id, titulo, autor, editorial, paginas, disponible);
    }
}
