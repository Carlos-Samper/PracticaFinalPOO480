package Logica;

import Modelo.*;
import Modelo.Abstractos.Articulo;
import Modelo.Concretos.DVD;
import Modelo.Concretos.Libro;
import Modelo.Concretos.Revista;
import Modelo.Concretos.Transaccion;
import Persistencia.*;

import java.time.LocalDate;
import java.util.List;

public class GestorBiblioteca {

    // Acceso a los DAOs para el Gestor
    private UsuarioDAO usuarioDAO;
    private ArticuloDAO articuloDAO;
    private TransaccionDAO transaccionDAO;

    public GestorBiblioteca() {
        this.usuarioDAO = new UsuarioDAO();
        this.articuloDAO = new ArticuloDAO();
        this.transaccionDAO = new TransaccionDAO();
    }

    public void registrarUsuario(Usuario usuario) throws Exception {
        if (usuarioDAO.obtenerUsuarioPorDni(usuario.getDni()) != null) {
            throw new Exception("Ya existe un usuario registrado con el DNI: " + usuario.getDni());
        }
        if (!usuarioDAO.registrarUsuario(usuario)) {
            throw new Exception("Error al registrar el usuario en la base de datos.");
        }
    }

    public void registrarArticulo(Articulo articulo) throws Exception {
        if (!articuloDAO.registrarArticulo(articulo)) {
            throw new Exception("Error al registrar el artículo en la base de datos.");
        }
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioDAO.listarTodos();
    }

    public List<Articulo> obtenerTodosLosArticulos() {
        return articuloDAO.listarTodos();
    }

    // Buscar cualquier Artículo por su ID (Por si lo usamos)
    public Articulo obtenerArticuloPorId(int id) {
        List<Articulo> articulos = articuloDAO.listarTodos();
        for (Articulo a : articulos) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }


    public void prestarArticulo(int idArticulo, String dniUsuario) throws Exception {
        // Validamos que el Usuario al que prestar Exista
        Usuario usuario = usuarioDAO.obtenerUsuarioPorDni(dniUsuario);
        if (usuario == null) {
            throw new Exception("El usuario con DNI " + dniUsuario + " no existe.");
        }

        // Validamos que el Articulo Existe y se encuentre Disponible
        Articulo articulo = obtenerArticuloPorId(idArticulo);
        if (articulo == null) {
            throw new Exception("El artículo seleccionado no existe.");
        }
        if (!articulo.isDisponible()) {
            throw new Exception("El artículo ya está prestado a otro usuario.");
        }

        // Hacemos Poliformismo para calcular la fecha de devolución de cada objeto según su tipo
        int diasPrestamo = 0;
        if (articulo instanceof Libro) diasPrestamo = 15;
        else if (articulo instanceof Revista) diasPrestamo = 7;
        else if (articulo instanceof DVD) diasPrestamo = 3;

        LocalDate fechaDevolucion = LocalDate.now().plusDays(diasPrestamo);

        // 4. Aplicar la lógica interna del objeto (cambiar su estado)
        articulo.prestar(dniUsuario, fechaDevolucion);

        // 5. Guardar los cambios en la base de datos
        if (!articuloDAO.actualizarEstado(articulo)) {
            throw new Exception("Error grave al actualizar el estado en la base de datos.");
        }

        // 6. Registrar la transacción en el historial
        Transaccion transaccion = new Transaccion("PRESTAMO", dniUsuario, idArticulo);
        transaccionDAO.registrarTransaccion(transaccion);
    }

    public void devolverArticulo(int idArticulo) throws Exception {
        // 1. Buscar el artículo
        Articulo articulo = obtenerArticuloPorId(idArticulo);
        if (articulo == null) {
            throw new Exception("El artículo no existe.");
        }
        if (articulo.isDisponible()) {
            throw new Exception("Este artículo ya consta como devuelto y disponible.");
        }

        // Guardamos el DNI de quien lo tenía antes de limpiar el estado para el historial
        String dniUsuarioQueDevuelve = articulo.getPrestadoADni();

        // 2. Aplicar la lógica interna del objeto (limpiar su estado)
        articulo.devolver();

        // 3. Guardar los cambios en la base de datos
        if (!articuloDAO.actualizarEstado(articulo)) {
            throw new Exception("Error grave al actualizar el estado en la base de datos.");
        }

        // 4. Registrar la transacción de devolución
        Transaccion transaccion = new Transaccion("DEVOLUCION", dniUsuarioQueDevuelve, idArticulo);
        transaccionDAO.registrarTransaccion(transaccion);
    }
}