package org.proyectofinal480.Modelo.Abstractos;

import java.time.LocalDate;

public interface IPrestable {
    int getDiasPrestamo();
    boolean isDisponible();
    void prestar(String dniUsuario, LocalDate fechaDevolucion);
    void devolver();
}
