package Modelo.Abstractos;

import java.time.LocalDate;

public interface IPrestable {
    boolean isDisponible();
    void prestar(String dniUsuario, LocalDate fechaDevolucion);
    void devolver();
}
