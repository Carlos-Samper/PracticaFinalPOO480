package org.example.proyectofinal480.Modelo.Abstractos;

import java.time.LocalDate;

public interface IPrestable {
    boolean isDisponible();
    void prestar(String dniUsuario, LocalDate fechaDevolucion);
    void devolver();
}
