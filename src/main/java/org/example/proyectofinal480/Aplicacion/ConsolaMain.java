package org.example.proyectofinal480.Aplicacion;

import org.example.proyectofinal480.Logica.GestorBiblioteca;
import org.example.proyectofinal480.Modelo.Abstractos.Articulo;
import org.example.proyectofinal480.Modelo.Concretos.DVD;
import org.example.proyectofinal480.Modelo.Concretos.Libro;
import org.example.proyectofinal480.Modelo.Concretos.Revista;
import org.example.proyectofinal480.Modelo.Concretos.Transaccion;
import org.example.proyectofinal480.Modelo.Usuario;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ConsolaMain {

    private static GestorBiblioteca gestor = new GestorBiblioteca();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n--- GESTION DE BIBLIOTECA 480 (CONSOLA) ---");
            System.out.println("1. Registrar Usuario");
            System.out.println("2. Registrar Articulo (Libro, Revista, DVD)");
            System.out.println("3. Listar Usuarios");
            System.out.println("4. Listar Articulos");
            System.out.println("5. Buscar Articulos por titulo");
            System.out.println("6. Prestar Articulo");
            System.out.println("7. Devolver Articulo");
            System.out.println("8. Historial de Transacciones");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            int opcion;
            try {
                opcion = Integer.parseInt(scanner.nextLine());
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
                case 0:
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }

    private static void registrarUsuario() {
        System.out.println("\n-- REGISTRO DE USUARIO --");
        System.out.print("DNI: ");
        String dni = scanner.nextLine();
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Telefono: ");
        String telefono = scanner.nextLine();

        Usuario u = new Usuario(dni, nombre, apellidos, email, telefono);
        try {
            gestor.registrarUsuario(u);
            System.out.println("Usuario registrado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al registrar: " + e.getMessage());
        }
    }

    private static void registrarArticulo() {
        System.out.println("\n-- REGISTRO DE ARTICULO --");
        System.out.print("Tipo (1=Libro, 2=Revista, 3=DVD): ");
        String tipoStr = scanner.nextLine();
        
        if (!tipoStr.equals("1") && !tipoStr.equals("2") && !tipoStr.equals("3")) {
            System.out.println("Tipo no valido.");
            return;
        }

        System.out.print("Titulo: ");
        String titulo = scanner.nextLine();

        Articulo articulo = null;
        try {
            switch (tipoStr) {
                case "1":
                    System.out.print("Autor: ");
                    String autor = scanner.nextLine();
                    System.out.print("ISBN: ");
                    String isbn = scanner.nextLine();
                    articulo = new Libro(0, titulo, autor, isbn);
                    break;
                case "2":
                    System.out.print("ISSN: ");
                    String issn = scanner.nextLine();
                    System.out.print("Numero: ");
                    int numero = Integer.parseInt(scanner.nextLine());
                    articulo = new Revista(0, titulo, issn, numero);
                    break;
                case "3":
                    System.out.print("Director: ");
                    String director = scanner.nextLine();
                    System.out.print("Duracion (minutos): ");
                    int duracion = Integer.parseInt(scanner.nextLine());
                    articulo = new DVD(0, titulo, director, duracion);
                    break;
            }
        } catch (NumberFormatException e) {
             System.out.println("Error: Formato numerico invalido.");
             return;
        } catch (Exception e) {
             System.out.println("Error al leer datos especificos: " + e.getMessage());
             return;
        }

        try {
            if (articulo != null) {
                gestor.registrarArticulo(articulo);
                System.out.println("Articulo registrado correctamente.");
            }
        } catch (Exception e) {
            System.out.println("Error al registrar articulo: " + e.getMessage());
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            System.out.println("Error al listar articulos: " + e.getMessage());
        }
    }

    private static void buscarArticulos() {
        System.out.println("\n-- BUSCAR ARTICULOS --");
        System.out.print("Ingrese el titulo (o parte del mismo): ");
        String tituloBusqueda = scanner.nextLine();
        
        try {
            List<Articulo> articulosEncontrados = gestor.buscarArticulosPorTitulo(tituloBusqueda);
            if (articulosEncontrados.isEmpty()) {
                System.out.println("No se encontraron articulos con ese titulo.");
                return;
            }
            System.out.println("Resultados de la busqueda:");
            for (Articulo a : articulosEncontrados) {
                String tipo = a.getClass().getSimpleName();
                String estado = a.isDisponible() ? "Disponible" : "Prestado a " + a.getPrestadoADni();
                System.out.println("ID: " + a.getId() + " | [" + tipo + "] " + a.getTitulo() + " - " + estado);
            }
        } catch (Exception e) {
            System.out.println("Error al buscar articulos: " + e.getMessage());
        }
    }

    private static void prestarArticulo() {
        System.out.println("\n-- PRESTAR ARTICULO --");
        try {
            System.out.print("ID del articulo: ");
            int idArticulo = Integer.parseInt(scanner.nextLine());
            System.out.print("DNI del usuario: ");
            String dni = scanner.nextLine();

            gestor.prestarArticulo(idArticulo, dni);
            System.out.println("Prestamo realizado con exito.");
        } catch (NumberFormatException e) {
            System.out.println("Error: El ID del articulo debe ser un numero.");
        } catch (Exception e) {
            System.out.println("Error al prestar: " + e.getMessage());
        }
    }

    private static void devolverArticulo() {
        System.out.println("\n-- DEVOLVER ARTICULO --");
        try {
            System.out.print("ID del articulo: ");
            int idArticulo = Integer.parseInt(scanner.nextLine());

            gestor.devolverArticulo(idArticulo);
            System.out.println("Devolucion realizada con exito.");
        } catch (NumberFormatException e) {
            System.out.println("Error: El ID del articulo debe ser un numero.");
        } catch (Exception e) {
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
                
                System.out.println(String.format("[%s] %s | %s (%s) | %s (DNI: %s)",
                        t.getFechaHora().format(formatter),
                        t.getTipoOperacion(),
                        tituloArticulo,
                        tipoArticulo,
                        nombreUsuario,
                        t.getDniUsuario()
                ));
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar historial: " + e.getMessage());
        }
    }
}