package org.example.proyectofinal480.Modelo.Concretos;

import org.example.proyectofinal480.Modelo.Abstractos.Articulo;

public class DVD extends Articulo {
    String director;
    int duracion;

    public DVD(int id, String titulo, String director, int duracion) {
        super(id, titulo);
        this.director = director;
        this.duracion = duracion;
    }

    @Override
    public String obtenerResumen() { return "DVD: " + titulo + " | Director: " + director + " | Duracion: " + duracion + " min"; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }
}
