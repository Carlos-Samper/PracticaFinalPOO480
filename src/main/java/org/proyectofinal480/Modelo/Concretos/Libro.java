package org.proyectofinal480.Modelo.Concretos;

import org.proyectofinal480.Modelo.Abstractos.Articulo;

public class Libro extends Articulo {

    public static final int MAX_DIAS_PRESTAMO = 15;
    private String autor;
    private String isbn;

    public Libro(int id, String titulo, String autor, String isbn) {
        super(id, titulo);
        this.autor = autor;
        this.isbn = isbn;
    }

    @Override
    public String obtenerResumen() { return "LIBRO: " + titulo + " | Autor: " + autor + " | ISBN: " + isbn; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    @Override
    public int getDiasPrestamo() {
        return MAX_DIAS_PRESTAMO;
    }
}

