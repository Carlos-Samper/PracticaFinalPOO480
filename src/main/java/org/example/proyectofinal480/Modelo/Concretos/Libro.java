package org.example.proyectofinal480.Modelo.Concretos;

import org.example.proyectofinal480.Modelo.Abstractos.Articulo;

public class Libro extends Articulo {
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
}

