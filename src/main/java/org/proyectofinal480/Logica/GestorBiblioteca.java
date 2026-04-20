package org.proyectofinal480.Logica;

import org.proyectofinal480.Excepciones.BibliotecaException;
import org.proyectofinal480.Excepciones.ValidacionException;
import org.proyectofinal480.Modelo.*;
import org.proyectofinal480.Modelo.Abstractos.Articulo;
import org.proyectofinal480.Persistencia.*;
import org.proyectofinal480.Modelo.Transaccion;
import org.proyectofinal480.Modelo.Usuario;
import org.proyectofinal480.Persistencia.ArticuloDAO;
import org.proyectofinal480.Persistencia.TransaccionDAO;
import org.proyectofinal480.Persistencia.UsuarioDAO;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class GestorBiblioteca {
    public static final double RECARGO_POR_DIA = 0.5;

    private UsuarioDAO usuarioDAO;
    private ArticuloDAO articuloDAO;
    private TransaccionDAO transaccionDAO;

    public GestorBiblioteca() {
        this.usuarioDAO = new UsuarioDAO();
        this.articuloDAO = new ArticuloDAO();
        this.transaccionDAO = new TransaccionDAO();
    }

    public void registrarUsuario(Usuario usuario) throws BibliotecaException {
        if (usuarioDAO.obtenerUsuarioPorDni(usuario.getDni()) != null) {
            throw new BibliotecaException("Ya existe un usuario registrado con el DNI: " + usuario.getDni());
        }
        usuarioDAO.registrarUsuario(usuario);
    }

    public void registrarArticulo(Articulo articulo) throws BibliotecaException {
        articuloDAO.registrarArticulo(articulo);
    }

    public List<Usuario> obtenerTodosLosUsuarios() throws BibliotecaException {
        return usuarioDAO.listarTodos();
    }

    public List<Articulo> obtenerTodosLosArticulos() throws BibliotecaException {
        return articuloDAO.listarTodos();
    }

    public Articulo obtenerArticuloPorId(int id) throws BibliotecaException {
        List<Articulo> articulos = articuloDAO.listarTodos();
        for (Articulo a : articulos) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    public Usuario obtenerUsuarioPorDni(String dni) throws BibliotecaException {
        return usuarioDAO.obtenerUsuarioPorDni(dni);
    }

    public List<Transaccion> obtenerTodasLasTransacciones() throws BibliotecaException {
        return transaccionDAO.listarTodos();
    }

    public List<Articulo> buscarArticulosPorTitulo(String tituloBusqueda) throws BibliotecaException {
        List<Articulo> articulos = articuloDAO.listarTodos();
        List<Articulo> resultados = new ArrayList<>();

        for (Articulo a : articulos) {
            if (a.getTitulo().toLowerCase().contains(tituloBusqueda.toLowerCase())) {
                resultados.add(a);
            }
        }
        return resultados;
    }

    public void prestarArticulo(int idArticulo, String dniUsuario) throws BibliotecaException {
        Usuario usuario = usuarioDAO.obtenerUsuarioPorDni(dniUsuario);
        if (usuario == null) {
            throw new BibliotecaException("El usuario con DNI " + dniUsuario + " no existe.");
        }

        Articulo articulo = obtenerArticuloPorId(idArticulo);
        if (articulo == null) {
            throw new BibliotecaException("El articulo seleccionado no existe.");
        }
        if (!articulo.isDisponible()) {
            throw new BibliotecaException("El articulo ya esta prestado a otro usuario.");
        }

        LocalDate fechaDevolucion = LocalDate.now().plusDays(articulo.getDiasPrestamo());

        articulo.prestar(dniUsuario, fechaDevolucion);

        articuloDAO.actualizarEstado(articulo);

        Transaccion transaccion = new Transaccion("PRESTAMO", dniUsuario, idArticulo);
        transaccionDAO.registrarTransaccion(transaccion);
    }

    public double devolverArticulo(int idArticulo) throws BibliotecaException {
        Articulo articulo = obtenerArticuloPorId(idArticulo);
        if (articulo == null) {
            throw new BibliotecaException("El articulo no existe.");
        }
        if (articulo.isDisponible()) {
            throw new BibliotecaException("Este articulo ya consta como devuelto y disponible.");
        }

        LocalDate fechaEsperada = articulo.getFechaDevolucion();
        LocalDate hoy = LocalDate.now();
        double recargoTotal = 0.0;

        if (fechaEsperada != null && hoy.isAfter(fechaEsperada)) {
            long diasRetraso = ChronoUnit.DAYS.between(fechaEsperada, hoy);
            recargoTotal = diasRetraso * RECARGO_POR_DIA;
        }

        String dniUsuarioQueDevuelve = articulo.getPrestadoADni();

        articulo.devolver();

        articuloDAO.actualizarEstado(articulo);

        Transaccion transaccion = new Transaccion("DEVOLUCION", dniUsuarioQueDevuelve, idArticulo);
        transaccionDAO.registrarTransaccion(transaccion);

        return recargoTotal;
    }

    public void eliminarArticulo(int id) throws BibliotecaException {
        Articulo articulo = obtenerArticuloPorId(id);
        if (articulo == null) {
            throw new BibliotecaException("No existe ningun articulo con ID: " + id);
        }
        if (!articulo.isDisponible()) {
            throw new BibliotecaException("No se puede eliminar el articulo porque esta prestado actualmente.");
        }
        articuloDAO.eliminarArticulo(id);
    }

    public void eliminarUsuario(String dni) throws BibliotecaException {
        if (usuarioDAO.obtenerUsuarioPorDni(dni) == null) {
            throw new BibliotecaException("No existe ningun usuario con DNI: " + dni);
        }
        List<Articulo> articulos = articuloDAO.listarTodos();
        for (Articulo a : articulos) {
            if (dni.equals(a.getPrestadoADni())) {
                throw new BibliotecaException("No se puede eliminar el usuario porque tiene articulos prestados.");
            }
        }
        usuarioDAO.eliminarUsuario(dni);
    }
}
