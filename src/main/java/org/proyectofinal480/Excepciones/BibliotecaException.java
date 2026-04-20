package org.proyectofinal480.Excepciones;

public class BibliotecaException extends Exception {
    public BibliotecaException(String mensaje) {
        super(mensaje);
    }

    public BibliotecaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
