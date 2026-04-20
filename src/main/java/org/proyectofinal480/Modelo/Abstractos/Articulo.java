package org.proyectofinal480.Modelo.Abstractos;

import java.time.LocalDate;

public abstract class Articulo implements IPrestable {

    protected int id;
    protected String titulo;
    protected boolean disponible;
    protected LocalDate fechaDevolucion;
    protected String prestadoADni;

    public Articulo(int id, String titulo) {
        this.id = id;
        this.titulo = titulo;
        this.disponible = true;
        this.fechaDevolucion = null;
        this.prestadoADni = null;
    }

    @Override
    public boolean isDisponible() {
        return this.disponible;
    }

    @Override
    public void prestar(String dniUsuario, LocalDate fechaDevolucion) {
        if (this.disponible) {
            this.disponible = false;
            this.prestadoADni = dniUsuario;
            this.fechaDevolucion = fechaDevolucion;
            System.out.println("Préstamo realizado correctamente.");
        }
    }

    @Override
    public void devolver() {
        if (!this.disponible) {
            this.disponible = true;
            this.prestadoADni = null;
            this.fechaDevolucion = null;
            System.out.println("Devolución realizada correctamente.");
        }
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }

    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public String getPrestadoADni() { return prestadoADni; }

    public void setPrestadoADni(String prestadoADni) { this.prestadoADni = prestadoADni; }

    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}
