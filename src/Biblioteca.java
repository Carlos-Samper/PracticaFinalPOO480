import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Biblioteca {



    // Listas de libros y revistas mezcladas sin estructura clara
    static ArrayList<String[]> libros = new ArrayList<>();
    // Cada String[]: [0]=titulo, [1]=autor, [2]=isbn, [3]=disponible(true/false),
    //                [4]=fechaDevolucion, [5]=prestadoA

    static ArrayList<String[]> revistas = new ArrayList<>();
    // Cada String[]: [0]=titulo, [1]=issn, [2]=numero, [3]=disponible(true/false),
    //                [4]=fechaDevolucion, [5]=prestadoA

    static ArrayList<String[]> dvds = new ArrayList<>();
    // Cada String[]: [0]=titulo, [1]=director, [2]=duracion, [3]=disponible(true/false),
    //                [4]=fechaDevolucion, [5]=prestadoA

    static ArrayList<String[]> usuarios = new ArrayList<>();
    // Cada String[]: [0]=nombre, [1]=apellidos, [2]=dni, [3]=email, [4]=telefono

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
       
        libros.add(new String[]{"El Quijote", "Cervantes", "978-1111", "true", "", ""});
        libros.add(new String[]{"Cien años de soledad", "García Márquez", "978-2222", "true", "", ""});
        revistas.add(new String[]{"National Geographic", "NGM-001", "345", "true", "", ""});
        dvds.add(new String[]{"El Padrino", "Coppola", "175", "true", "", ""});
        usuarios.add(new String[]{"Juan", "García López", "12345678A", "juan@email.com", "600111222"});
        usuarios.add(new String[]{"María", "Pérez Ruiz", "87654321B", "maria@email.com", "600333444"});

        int opcion = 0;
        do {
            System.out.println("\n=== BIBLIOTECA ===");
            System.out.println("1. Registrar libro");
            System.out.println("2. Registrar revista");
            System.out.println("3. Registrar DVD");
            System.out.println("4. Registrar usuario");
            System.out.println("5. Prestar libro");
            System.out.println("6. Prestar revista");
            System.out.println("7. Prestar DVD");
            System.out.println("8. Devolver libro");
            System.out.println("9. Devolver revista");
            System.out.println("10. Devolver DVD");
            System.out.println("11. Buscar libro por titulo");
            System.out.println("12. Buscar revista por titulo");
            System.out.println("13. Buscar DVD por titulo");
            System.out.println("14. Ver libros disponibles");
            System.out.println("15. Ver revistas disponibles");
            System.out.println("16. Ver DVDs disponibles");
            System.out.println("17. Ver libros prestados");
            System.out.println("18. Ver revistas prestadas");
            System.out.println("19. Ver DVDs prestados");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            opcion = sc.nextInt(); sc.nextLine();

            if (opcion == 1) registrarLibro();
            else if (opcion == 2) registrarRevista();
            else if (opcion == 3) registrarDVD();
            else if (opcion == 4) registrarUsuario();
            else if (opcion == 5) prestarLibro();
            else if (opcion == 6) prestarRevista();
            else if (opcion == 7) prestarDVD();
            else if (opcion == 8) devolverLibro();
            else if (opcion == 9) devolverRevista();
            else if (opcion == 10) devolverDVD();
            else if (opcion == 11) buscarLibro();
            else if (opcion == 12) buscarRevista();
            else if (opcion == 13) buscarDVD();
            else if (opcion == 14) listarLibrosDisponibles();
            else if (opcion == 15) listarRevistasDisponibles();
            else if (opcion == 16) listarDVDsDisponibles();
            else if (opcion == 17) listarLibrosPrestados();
            else if (opcion == 18) listarRevistasPrestadas();
            else if (opcion == 19) listarDVDsPrestados();
        } while (opcion != 0);

        System.out.println("Hasta pronto.");
    }

    // ---- REGISTRAR ----
    static void registrarLibro() {
        System.out.print("Titulo: "); String titulo = sc.nextLine();
        System.out.print("Autor: "); String autor = sc.nextLine();
        System.out.print("ISBN: "); String isbn = sc.nextLine();
        libros.add(new String[]{titulo, autor, isbn, "true", "", ""});
        System.out.println("Libro registrado.");
    }

    static void registrarRevista() {
        System.out.print("Titulo: "); String titulo = sc.nextLine();
        System.out.print("ISSN: "); String issn = sc.nextLine();
        System.out.print("Numero: "); String numero = sc.nextLine();
        revistas.add(new String[]{titulo, issn, numero, "true", "", ""});
        System.out.println("Revista registrada.");
    }

    static void registrarDVD() {
        System.out.print("Titulo: "); String titulo = sc.nextLine();
        System.out.print("Director: "); String director = sc.nextLine();
        System.out.print("Duracion (min): "); String duracion = sc.nextLine();
        dvds.add(new String[]{titulo, director, duracion, "true", "", ""});
        System.out.println("DVD registrado.");
    }

    static void registrarUsuario() {
        System.out.print("Nombre: "); String nombre = sc.nextLine();
        System.out.print("Apellidos: "); String apellidos = sc.nextLine();
        System.out.print("DNI: "); String dni = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();
        System.out.print("Telefono: "); String telefono = sc.nextLine();
        usuarios.add(new String[]{nombre, apellidos, dni, email, telefono});
        System.out.println("Usuario registrado.");
    }

    // ---- PRESTAR ----
    static void prestarLibro() {
        System.out.print("ISBN del libro a prestar: "); String isbn = sc.nextLine();
        System.out.print("DNI del usuario: "); String dni = sc.nextLine();
        // Buscar usuario
        boolean usuarioEncontrado = false;
        for (String[] u : usuarios) {
            if (u[2].equals(dni)) { usuarioEncontrado = true; break; }
        }
        if (!usuarioEncontrado) { System.out.println("Usuario no encontrado."); return; }
        // Buscar libro
        for (String[] l : libros) {
            if (l[2].equals(isbn)) {
                if (l[3].equals("true")) {
                    l[3] = "false";
                    l[5] = dni;
                    // Fecha devolucion: 15 dias
                    l[4] = new Date(System.currentTimeMillis() + 15L * 24 * 60 * 60 * 1000).toString();
                    System.out.println("Libro prestado. Devolver antes de: " + l[4]);
                } else {
                    System.out.println("El libro no esta disponible. Prestado a: " + l[5]);
                }
                return;
            }
        }
        System.out.println("Libro no encontrado.");
    }

    static void prestarRevista() {
        System.out.print("ISSN de la revista: "); String issn = sc.nextLine();
        System.out.print("Numero de la revista: "); String numero = sc.nextLine();
        System.out.print("DNI del usuario: "); String dni = sc.nextLine();
        boolean usuarioEncontrado = false;
        for (String[] u : usuarios) {
            if (u[2].equals(dni)) { usuarioEncontrado = true; break; }
        }
        if (!usuarioEncontrado) { System.out.println("Usuario no encontrado."); return; }
        for (String[] r : revistas) {
            if (r[1].equals(issn) && r[2].equals(numero)) {
                if (r[3].equals("true")) {
                    r[3] = "false";
                    r[5] = dni;
                    // Fecha devolucion: 7 dias (revistas menos tiempo)
                    r[4] = new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000).toString();
                    System.out.println("Revista prestada. Devolver antes de: " + r[4]);
                } else {
                    System.out.println("La revista no esta disponible. Prestada a: " + r[5]);
                }
                return;
            }
        }
        System.out.println("Revista no encontrada.");
    }

    static void prestarDVD() {
        System.out.print("Titulo del DVD: "); String titulo = sc.nextLine();
        System.out.print("DNI del usuario: "); String dni = sc.nextLine();
        boolean usuarioEncontrado = false;
        for (String[] u : usuarios) {
            if (u[2].equals(dni)) { usuarioEncontrado = true; break; }
        }
        if (!usuarioEncontrado) { System.out.println("Usuario no encontrado."); return; }
        for (String[] d : dvds) {
            if (d[0].equalsIgnoreCase(titulo)) {
                if (d[3].equals("true")) {
                    d[3] = "false";
                    d[5] = dni;
                    // Fecha devolucion: 3 dias (DVDs menos tiempo aun)
                    d[4] = new Date(System.currentTimeMillis() + 3L * 24 * 60 * 60 * 1000).toString();
                    System.out.println("DVD prestado. Devolver antes de: " + d[4]);
                } else {
                    System.out.println("El DVD no esta disponible. Prestado a: " + d[5]);
                }
                return;
            }
        }
        System.out.println("DVD no encontrado.");
    }

    // ---- DEVOLVER ----
    static void devolverLibro() {
        System.out.print("ISBN del libro a devolver: "); String isbn = sc.nextLine();
        for (String[] l : libros) {
            if (l[2].equals(isbn)) {
                if (l[3].equals("false")) {
                    // Calcular multa (si se devuelve tarde, 0.50€/dia - logica simplificada)
                    l[3] = "true";
                    l[4] = "";
                    String prestadoA = l[5];
                    l[5] = "";
                    System.out.println("Libro devuelto correctamente. Estaba a nombre de: " + prestadoA);
                } else {
                    System.out.println("Este libro no estaba prestado.");
                }
                return;
            }
        }
        System.out.println("Libro no encontrado.");
    }

    static void devolverRevista() {
        System.out.print("ISSN de la revista: "); String issn = sc.nextLine();
        System.out.print("Numero: "); String numero = sc.nextLine();
        for (String[] r : revistas) {
            if (r[1].equals(issn) && r[2].equals(numero)) {
                if (r[3].equals("false")) {
                    r[3] = "true";
                    r[4] = "";
                    String prestadoA = r[5];
                    r[5] = "";
                    System.out.println("Revista devuelta. Estaba a nombre de: " + prestadoA);
                } else {
                    System.out.println("Esta revista no estaba prestada.");
                }
                return;
            }
        }
        System.out.println("Revista no encontrada.");
    }

    static void devolverDVD() {
        System.out.print("Titulo del DVD: "); String titulo = sc.nextLine();
        for (String[] d : dvds) {
            if (d[0].equalsIgnoreCase(titulo)) {
                if (d[3].equals("false")) {
                    d[3] = "true";
                    d[4] = "";
                    String prestadoA = d[5];
                    d[5] = "";
                    System.out.println("DVD devuelto. Estaba a nombre de: " + prestadoA);
                } else {
                    System.out.println("Este DVD no estaba prestado.");
                }
                return;
            }
        }
        System.out.println("DVD no encontrado.");
    }

    // ---- BUSCAR ----
    static void buscarLibro() {
        System.out.print("Titulo (o parte): "); String titulo = sc.nextLine();
        boolean encontrado = false;
        for (String[] l : libros) {
            if (l[0].toLowerCase().contains(titulo.toLowerCase())) {
                System.out.println("Titulo: " + l[0] + " | Autor: " + l[1] + " | ISBN: " + l[2]
                        + " | Disponible: " + l[3]);
                encontrado = true;
            }
        }
        if (!encontrado) System.out.println("No se encontraron libros.");
    }

    static void buscarRevista() {
        System.out.print("Titulo (o parte): "); String titulo = sc.nextLine();
        boolean encontrado = false;
        for (String[] r : revistas) {
            if (r[0].toLowerCase().contains(titulo.toLowerCase())) {
                System.out.println("Titulo: " + r[0] + " | ISSN: " + r[1] + " | Num: " + r[2]
                        + " | Disponible: " + r[3]);
                encontrado = true;
            }
        }
        if (!encontrado) System.out.println("No se encontraron revistas.");
    }

    static void buscarDVD() {
        System.out.print("Titulo (o parte): "); String titulo = sc.nextLine();
        boolean encontrado = false;
        for (String[] d : dvds) {
            if (d[0].toLowerCase().contains(titulo.toLowerCase())) {
                System.out.println("Titulo: " + d[0] + " | Director: " + d[1] + " | Duracion: " + d[2]
                        + " min | Disponible: " + d[3]);
                encontrado = true;
            }
        }
        if (!encontrado) System.out.println("No se encontraron DVDs.");
    }

    // ---- LISTAR ----
    static void listarLibrosDisponibles() {
        System.out.println("--- Libros disponibles ---");
        boolean hay = false;
        for (String[] l : libros) {
            if (l[3].equals("true")) {
                System.out.println("  " + l[0] + " - " + l[1] + " [" + l[2] + "]");
                hay = true;
            }
        }
        if (!hay) System.out.println("  No hay libros disponibles.");
    }

    static void listarRevistasDisponibles() {
        System.out.println("--- Revistas disponibles ---");
        boolean hay = false;
        for (String[] r : revistas) {
            if (r[3].equals("true")) {
                System.out.println("  " + r[0] + " num." + r[2] + " [" + r[1] + "]");
                hay = true;
            }
        }
        if (!hay) System.out.println("  No hay revistas disponibles.");
    }

    static void listarDVDsDisponibles() {
        System.out.println("--- DVDs disponibles ---");
        boolean hay = false;
        for (String[] d : dvds) {
            if (d[3].equals("true")) {
                System.out.println("  " + d[0] + " - Dir: " + d[1] + " (" + d[2] + " min)");
                hay = true;
            }
        }
        if (!hay) System.out.println("  No hay DVDs disponibles.");
    }

    static void listarLibrosPrestados() {
        System.out.println("--- Libros prestados ---");
        boolean hay = false;
        for (String[] l : libros) {
            if (l[3].equals("false")) {
                System.out.println("  " + l[0] + " [" + l[2] + "] -> prestado a: " + l[5] + " | devolver: " + l[4]);
                hay = true;
            }
        }
        if (!hay) System.out.println("  No hay libros prestados.");
    }

    static void listarRevistasPrestadas() {
        System.out.println("--- Revistas prestadas ---");
        boolean hay = false;
        for (String[] r : revistas) {
            if (r[3].equals("false")) {
                System.out.println("  " + r[0] + " num." + r[2] + " -> prestada a: " + r[5] + " | devolver: " + r[4]);
                hay = true;
            }
        }
        if (!hay) System.out.println("  No hay revistas prestadas.");
    }

    static void listarDVDsPrestados() {
        System.out.println("--- DVDs prestados ---");
        boolean hay = false;
        for (String[] d : dvds) {
            if (d[3].equals("false")) {
                System.out.println("  " + d[0] + " -> prestado a: " + d[5] + " | devolver: " + d[4]);
                hay = true;
            }
        }
        if (!hay) System.out.println("  No hay DVDs prestados.");
    }
}
