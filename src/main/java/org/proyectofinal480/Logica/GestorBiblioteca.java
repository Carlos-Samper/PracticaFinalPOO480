package org.proyectofinal480.Logica;

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

    public void registrarUsuario(Usuario usuario) throws Exception {
        if (usuarioDAO.obtenerUsuarioPorDni(usuario.getDni()) != null) {
            throw new Exception("Ya existe un usuario registrado con el DNI: " + usuario.getDni());
        }
        if (!usuarioDAO.registrarUsuario(usuario)) {
            throw new Exception("Error al guardar el usuario en la base de datos.");
        }
    }

    public void registrarArticulo(Articulo articulo) throws Exception {
        if (!articuloDAO.registrarArticulo(articulo)) {
            throw new Exception("Error al guardar el articulo en la base de datos.");
        }
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioDAO.listarTodos();
    }

    public List<Articulo> obtenerTodosLosArticulos() {
        return articuloDAO.listarTodos();
    }

    public Articulo obtenerArticuloPorId(int id) {
        List<Articulo> articulos = articuloDAO.listarTodos();
        for (Articulo a : articulos) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    public Usuario obtenerUsuarioPorDni(String dni) {
        return usuarioDAO.obtenerUsuarioPorDni(dni);
    }

    public List<Transaccion> obtenerTodasLasTransacciones() {
        return transaccionDAO.listarTodos();
    }

    public List<Articulo> buscarArticulosPorTitulo(String tituloBusqueda) {
        List<Articulo> articulos = articuloDAO.listarTodos();
        List<Articulo> resultados = new ArrayList<>();
        
        for (Articulo a : articulos) {
            if (a.getTitulo().toLowerCase().contains(tituloBusqueda.toLowerCase())) {
                resultados.add(a);
            }
        }
        return resultados;
    }

    public void prestarArticulo(int idArticulo, String dniUsuario) throws Exception {
        Usuario usuario = usuarioDAO.obtenerUsuarioPorDni(dniUsuario);
        if (usuario == null) {
            throw new Exception("El usuario con DNI " + dniUsuario + " no existe.");
        }

        Articulo articulo = obtenerArticuloPorId(idArticulo);
        if (articulo == null) {
            throw new Exception("El articulo seleccionado no existe.");
        }
        if (!articulo.isDisponible()) {
            throw new Exception("El articulo ya esta prestado a otro usuario.");
        }


        LocalDate fechaDevolucion = LocalDate.now().plusDays(articulo.getDiasPrestamo());

        articulo.prestar(dniUsuario, fechaDevolucion);

        if (!articuloDAO.actualizarEstado(articulo)) {
            throw new Exception("Error grave al actualizar el estado en la base de datos.");
        }

        Transaccion transaccion = new Transaccion("PRESTAMO", dniUsuario, idArticulo);
        transaccionDAO.registrarTransaccion(transaccion);
    }

    public void devolverArticulo(int idArticulo) throws Exception {
        Articulo articulo = obtenerArticuloPorId(idArticulo);
        if (articulo == null) {
            throw new Exception("El articulo no existe.");
        }
        if (articulo.isDisponible()) {
            throw new Exception("Este articulo ya consta como devuelto y disponible.");
        }

        LocalDate fechaEsperada = articulo.getFechaDevolucion();
        LocalDate hoy = LocalDate.now();

        if (fechaEsperada != null && hoy.isAfter(fechaEsperada)) {
            long diasRetraso = ChronoUnit.DAYS.between(fechaEsperada, hoy);
            double recargoTotal = diasRetraso * RECARGO_POR_DIA;
            System.out.println("ATENCION: El articulo se devuelve con " + diasRetraso + " dias de retraso.");
            System.out.println("Debe pagar un recargo de: " + recargoTotal + " euros.");
        }

        String dniUsuarioQueDevuelve = articulo.getPrestadoADni();

        articulo.devolver();

        if (!articuloDAO.actualizarEstado(articulo)) {
            throw new Exception("Error grave al actualizar el estado en la base de datos.");
        }

        Transaccion transaccion = new Transaccion("DEVOLUCION", dniUsuarioQueDevuelve, idArticulo);
        transaccionDAO.registrarTransaccion(transaccion);
    }
}