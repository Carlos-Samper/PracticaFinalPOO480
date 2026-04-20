package org.proyectofinal480.Aplicacion;

import org.proyectofinal480.Excepciones.BibliotecaException;
import org.proyectofinal480.Excepciones.ValidacionException;
import org.proyectofinal480.Logica.GestorBiblioteca;
import org.proyectofinal480.Logica.Validador;
import org.proyectofinal480.Modelo.Abstractos.Articulo;
import org.proyectofinal480.Modelo.Concretos.DVD;
import org.proyectofinal480.Modelo.Concretos.Libro;
import org.proyectofinal480.Modelo.Concretos.Revista;
import org.proyectofinal480.Modelo.Transaccion;
import org.proyectofinal480.Modelo.Usuario;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class AppConsola {

    private static final GestorBiblioteca gestor = new GestorBiblioteca();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            showMenu();

            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un numero valido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    registrarUsuario();
                    break;
                case 2:
                    registrarArticulo();
                    break;
                case 3:
                    listarUsuarios();
                    break;
                case 4:
                    listarArticulos();
                    break;
                case 5:
                    buscarArticulos();
                    break;
                case 6:
                    prestarArticulo();
                    break;
                case 7:
                    devolverArticulo();
                    break;
                case 8:
                    mostrarHistorial();
                    break;
                case 9:
                    eliminarArticulo();
                    break;
                case 10:
                    eliminarUsuario();
                    break;
                case 0:
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n--- GESTION DE BIBLIOTECA 480 (CONSOLA) ---");
        System.out.println("1. Registrar Usuario");
        System.out.println("2. Registrar Articulo (Libro, Revista, DVD)");
        System.out.println("3. Listar Usuarios");
        System.out.println("4. Listar Articulos");
        System.out.println("5. Buscar Articulos por titulo");
        System.out.println("6. Prestar Articulo");
        System.out.println("7. Devolver Articulo");
        System.out.println("8. Historial de Transacciones");
        System.out.println("9. Eliminar Articulo");
        System.out.println("10. Eliminar Usuario");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    private static void registrarUsuario() {
        System.out.println("\n-- REGISTRO DE USUARIO --");
        try {
            System.out.print("DNI (ej: 12345678A): ");
            String dni = scanner.nextLine().trim();
            Validador.validarDni(dni);

            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();
            Validador.validarNoVacio(nombre, "Nombre");

            System.out.print("Apellidos: ");
            String apellidos = scanner.nextLine().trim();
            Validador.validarNoVacio(apellidos, "Apellidos");

            System.out.print("Email (opcional, ej: usuario@email.com): ");
            String email = scanner.nextLine().trim();
            Validador.validarEmail(email);

            System.out.print("Telefono (opcional, ej: 612345678): ");
            String telefono = scanner.nextLine().trim();
            Validador.validarTelefono(telefono);

            Usuario u = new Usuario(dni, nombre, apellidos, email, telefono);
            gestor.registrarUsuario(u);
            System.out.println("Usuario registrado correctamente.");
        } catch (BibliotecaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registrarArticulo() {
        System.out.println("\n-- REGISTRO DE ARTICULO --");
        System.out.print("Tipo (1=Libro, 2=Revista, 3=DVD): ");
        String tipoStr = scanner.nextLine().trim();

        if (!tipoStr.equals("1") && !tipoStr.equals("2") && !tipoStr.equals("3")) {
            System.out.println("Tipo no valido.");
            return;
        }

        System.out.print("Titulo: ");
        String titulo = scanner.nextLine().trim();

        Articulo articulo = null;
        try {
            switch (tipoStr) {
                case "1":
                    System.out.print("Autor: ");
                    String autor = scanner.nextLine().trim();
                    Validador.validarNoVacio(autor, "Autor");
                    System.out.print("ISBN (ej: 978-84-376-0494-7): ");
                    String isbn = scanner.nextLine().trim();
                    Validador.validarIsbn(isbn);
                    articulo = new Libro(0, titulo, autor, isbn);
                    break;
                case "2":
                    System.out.print("ISSN (ej: 1234-567X): ");
                    String issn = scanner.nextLine().trim();
                    Validador.validarIssn(issn);
                    System.out.print("Numero (entero positivo): ");
                    String numStr = scanner.nextLine().trim();
                    Validador.validarNumeroPositivo(numStr, "Numero");
                    articulo = new Revista(0, titulo, issn, Integer.parseInt(numStr));
                    break;
                case "3":
                    System.out.print("Director: ");
                    String director = scanner.nextLine().trim();
                    Validador.validarNoVacio(director, "Director");
                    System.out.print("Duracion en minutos (entero positivo): ");
                    String durStr = scanner.nextLine().trim();
                    Validador.validarNumeroPositivo(durStr, "Duracion");
                    articulo = new DVD(0, titulo, director, Integer.parseInt(durStr));
                    break;
            }

            if (articulo != null) {
                Validador.validarNoVacio(titulo, "Titulo");
                gestor.registrarArticulo(articulo);
                System.out.println("Articulo registrado correctamente.");
            }
        } catch (BibliotecaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarUsuarios() {
        System.out.println("\n-- LISTA DE USUARIOS --");
        try {
            List<Usuario> usuarios = gestor.obtenerTodosLosUsuarios();
            if (usuarios.isEmpty()) {
                System.out.println("No hay usuarios registrados.");
                return;
            }
            for (Usuario u : usuarios) {
                System.out.println("DNI: " + u.getDni() + " | Nombre: " + u.getNombreCompleto());
            }
        } catch (BibliotecaException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }
    }

    private static void listarArticulos() {
        System.out.println("\n-- LISTA DE ARTICULOS --");
        try {
            List<Articulo> articulos = gestor.obtenerTodosLosArticulos();
            if (articulos.isEmpty()) {
                System.out.println("No hay articulos registrados.");
                return;
            }
            for (Articulo a : articulos) {
                String tipo = a.getClass().getSimpleName();
                String estado = a.isDisponible() ? "Disponible" : "Prestado a " + a.getPrestadoADni();
                System.out.println("ID: " + a.getId() + " | [" + tipo + "] " + a.getTitulo() + " - " + estado);
            }
        } catch (BibliotecaException e) {
            System.out.println("Error al listar articulos: " + e.getMessage());
        }
    }

    private static void buscarArticulos() {
        System.out.println("\n-- BUSCAR ARTICULOS --");
        System.out.print("Ingrese el titulo (o parte del mismo): ");
        String tituloBusqueda = scanner.nextLine().trim();

        try {
            List<Articulo> articulosEncontrados = gestor.buscarArticulosPorTitulo(tituloBusqueda);
            if (articulosEncontrados.isEmpty()) {
                System.out.println("No se encontraron articulos con ese titulo.");
                return;
            }
            System.out.println("Resultados:");
            for (Articulo a : articulosEncontrados) {
                String tipo = a.getClass().getSimpleName();
                String estado = a.isDisponible() ? "Disponible" : "Prestado a " + a.getPrestadoADni();
                System.out.println("ID: " + a.getId() + " | [" + tipo + "] " + a.getTitulo() + " - " + estado);
            }
        } catch (BibliotecaException e) {
            System.out.println("Error al buscar articulos: " + e.getMessage());
        }
    }

    private static void prestarArticulo() {
        System.out.println("\n-- PRESTAR ARTICULO --");
        try {
            System.out.print("ID del articulo: ");
            String idStr = scanner.nextLine().trim();
            Validador.validarNumeroPositivo(idStr, "ID Articulo");

            System.out.print("DNI del usuario (ej: 12345678A): ");
            String dni = scanner.nextLine().trim();
            Validador.validarDni(dni);

            gestor.prestarArticulo(Integer.parseInt(idStr), dni);
            System.out.println("Prestamo realizado con exito.");
        } catch (BibliotecaException e) {
            System.out.println("Error al prestar: " + e.getMessage());
        }
    }

    private static void devolverArticulo() {
        System.out.println("\n-- DEVOLVER ARTICULO --");
        try {
            System.out.print("ID del articulo: ");
            String idStr = scanner.nextLine().trim();
            Validador.validarNumeroPositivo(idStr, "ID Articulo");

            double recargo = gestor.devolverArticulo(Integer.parseInt(idStr));
            System.out.println("Devolucion realizada con exito.");
            if (recargo > 0) {
                System.out.printf("ATENCION: Devolucion con retraso. Recargo a abonar: %.2f EUR.%n", recargo);
            }
        } catch (BibliotecaException e) {
            System.out.println("Error al devolver: " + e.getMessage());
        }
    }

    private static void mostrarHistorial() {
        System.out.println("\n-- HISTORIAL DE TRANSACCIONES --");
        try {
            List<Transaccion> transacciones = gestor.obtenerTodasLasTransacciones();
            if (transacciones.isEmpty()) {
                System.out.println("No hay transacciones registradas.");
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            for (Transaccion t : transacciones) {
                Articulo articulo = gestor.obtenerArticuloPorId(t.getIdArticulo());
                Usuario usuario = gestor.obtenerUsuarioPorDni(t.getDniUsuario());

                String tituloArticulo = (articulo != null) ? articulo.getTitulo() : "Articulo Desconocido";
                String tipoArticulo = (articulo != null) ? articulo.getClass().getSimpleName() : "Desconocido";
                String nombreUsuario = (usuario != null) ? usuario.getNombreCompleto() : "Usuario Desconocido";

                System.out.printf("[%s] %s | %s (%s) | %s (DNI: %s)%n",
                        t.getFechaHora().format(formatter),
                        t.getTipoOperacion(),
                        tituloArticulo,
                        tipoArticulo,
                        nombreUsuario,
                        t.getDniUsuario());
            }
        } catch (BibliotecaException e) {
            System.out.println("Error al mostrar historial: " + e.getMessage());
        }
    }

    private static void eliminarArticulo() {
        System.out.println("\n-- ELIMINAR ARTICULO --");
        try {
            listarArticulos();
            System.out.print("ID del articulo a eliminar: ");
            String idStr = scanner.nextLine().trim();
            Validador.validarNumeroPositivo(idStr, "ID Articulo");

            int id = Integer.parseInt(idStr);
            Articulo articulo = gestor.obtenerArticuloPorId(id);
            if (articulo == null) {
                System.out.println("No existe ningun articulo con ese ID.");
                return;
            }

            System.out.print("Confirmar eliminacion del articulo '" + articulo.getTitulo() + "' (s/n): ");
            String confirmacion = scanner.nextLine().trim().toLowerCase();
            if (!confirmacion.equals("s")) {
                System.out.println("Operacion cancelada.");
                return;
            }

            gestor.eliminarArticulo(id);
            System.out.println("Articulo eliminado correctamente.");
        } catch (BibliotecaException e) {
            System.out.println("Error al eliminar articulo: " + e.getMessage());
        }
    }

    private static void eliminarUsuario() {
        System.out.println("\n-- ELIMINAR USUARIO --");
        try {
            System.out.print("DNI del usuario a eliminar (ej: 12345678A): ");
            String dni = scanner.nextLine().trim();
            Validador.validarDni(dni);

            Usuario usuario = gestor.obtenerUsuarioPorDni(dni);
            if (usuario == null) {
                System.out.println("No existe ningun usuario con ese DNI.");
                return;
            }

            System.out.print("Confirmar eliminacion del usuario '" + usuario.getNombreCompleto() + "' (s/n): ");
            String confirmacion = scanner.nextLine().trim().toLowerCase();
            if (!confirmacion.equals("s")) {
                System.out.println("Operacion cancelada.");
                return;
            }

            gestor.eliminarUsuario(dni);
            System.out.println("Usuario eliminado correctamente.");
        } catch (BibliotecaException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
    }
}
