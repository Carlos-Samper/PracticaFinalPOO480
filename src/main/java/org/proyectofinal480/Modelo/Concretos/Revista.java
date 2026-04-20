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

    @Override
    public String obtenerResumen() { return "REVISTA: " + titulo + " | ISSN: " + issn + " | Num: " + numero; }

    public String getIssn() { return issn; }
    public void setIssn(String issn) { this.issn = issn; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    @Override
    public int getDiasPrestamo() {
        return MAX_DIAS_PRESTAMO;
    }
}

