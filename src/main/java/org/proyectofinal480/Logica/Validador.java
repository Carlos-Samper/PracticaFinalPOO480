package org.proyectofinal480.Logica;

import org.proyectofinal480.Excepciones.ValidacionException;

public class Validador {

    public static void validarDni(String dni) throws ValidacionException {
        if (dni == null || !dni.matches("^\\d{8}[A-Z]$")) {
            throw new ValidacionException("DNI invalido. Formato esperado: 8 digitos seguidos de una letra mayuscula (ej: 12345678A).");
        }
    }

    public static void validarIsbn(String isbn) throws ValidacionException {
        if (isbn == null) throw new ValidacionException("El ISBN no puede estar vacio.");
        String soloDigitos = isbn.replaceAll("[-\\s]", "");
        if (!soloDigitos.matches("^\\d{10}$") && !soloDigitos.matches("^\\d{13}$")) {
            throw new ValidacionException("ISBN invalido. Debe contener 10 o 13 digitos (guiones opcionales, ej: 978-84-376-0494-7).");
        }
    }

    public static void validarIssn(String issn) throws ValidacionException {
        if (issn == null || !issn.matches("^\\d{4}-\\d{3}[\\dX]$")) {
            throw new ValidacionException("ISSN invalido. Formato esperado: 4 digitos, guion, 3 digitos y un digito o X (ej: 1234-567X).");
        }
    }

    public static void validarEmail(String email) throws ValidacionException {
        if (email == null || email.isEmpty()) return;
        if (!email.matches("^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$")) {
            throw new ValidacionException("Email invalido. Formato esperado: usuario@dominio.ext");
        }
    }

    public static void validarTelefono(String telefono) throws ValidacionException {
        if (telefono == null || telefono.isEmpty()) return;
        if (!telefono.matches("^[6-9]\\d{8}$")) {
            throw new ValidacionException("Telefono invalido. Debe tener 9 digitos y empezar por 6, 7, 8 o 9 (ej: 612345678).");
        }
    }

    public static void validarNoVacio(String valor, String nombreCampo) throws ValidacionException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionException("El campo '" + nombreCampo + "' es obligatorio.");
        }
    }

    public static void validarNumeroPositivo(String valor, String nombreCampo) throws ValidacionException {
        try {
            int num = Integer.parseInt(valor.trim());
            if (num <= 0) throw new ValidacionException("El campo '" + nombreCampo + "' debe ser un numero positivo.");
        } catch (NumberFormatException e) {
            throw new ValidacionException("El campo '" + nombreCampo + "' debe ser un numero entero valido.");
        }
    }
}
