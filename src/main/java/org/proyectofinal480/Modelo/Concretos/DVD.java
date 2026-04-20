package org.proyectofinal480.Modelo.Concretos;

import org.proyectofinal480.Modelo.Abstractos.Articulo;

public class DVD extends Articulo {

    public static final int MAX_DIAS_PRESTAMO = 3;
    String director;
    int duracion;

    public DVD(int id, String titulo, String director, int duracion) {
        super(id, titulo);
        this.director = director;
        this.duracion = duracion;
    }

    public String getDirector() { return director; }

    public int getDuracion() { return duracion; }

    @Override
    public int getDiasPrestamo() {
        return MAX_DIAS_PRESTAMO;
    }

    @Override
    public String toString() {
        return "DVD: " + titulo + " | Director: " + director + " | Duracion: " + duracion + " min";
    }
}
