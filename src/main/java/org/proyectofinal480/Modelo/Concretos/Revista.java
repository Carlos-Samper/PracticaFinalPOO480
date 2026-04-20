package org.proyectofinal480.Modelo.Concretos;

import org.proyectofinal480.Modelo.Abstractos.Articulo;

public class Revista extends Articulo {

    public static final int MAX_DIAS_PRESTAMO = 7;
    private String issn;
    private int numero;

    public Revista(int id, String titulo, String issn, int numero) {
        super(id, titulo);
        this.issn = issn;
        this.numero = numero;
    }

    public String getIssn() { return issn; }

    public int getNumero() { return numero; }

    @Override
    public int getDiasPrestamo() {
        return MAX_DIAS_PRESTAMO;
    }

    @Override
    public String toString() {
        return "REVISTA: " + titulo + " | ISSN: " + issn + " | Num: " + numero;
    }
}

