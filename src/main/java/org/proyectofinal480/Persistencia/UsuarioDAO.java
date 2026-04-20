package org.proyectofinal480.Persistencia;

import org.proyectofinal480.Excepciones.BibliotecaException;
import org.proyectofinal480.Modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public boolean registrarUsuario(Usuario usuario) throws BibliotecaException {
        String sql = "INSERT INTO Usuarios (dni, nombre, apellidos, email, telefono) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getDni());
            pstmt.setString(2, usuario.getNombre());
            pstmt.setString(3, usuario.getApellidos());
            pstmt.setString(4, usuario.getEmail());
            pstmt.setString(5, usuario.getTelefono());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new BibliotecaException("Error de base de datos al registrar usuario: " + e.getMessage(), e);
        }
    }

    public Usuario obtenerUsuarioPorDni(String dni) throws BibliotecaException {
        String sql = "SELECT * FROM Usuarios WHERE dni = ?";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("email"),
                        rs.getString("telefono")
                );
            }
        } catch (SQLException e) {
            throw new BibliotecaException("Error de base de datos al buscar usuario: " + e.getMessage(), e);
        }
        return null;
    }

    public List<Usuario> listarTodos() throws BibliotecaException {
        List<Usuario> listaUsuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                listaUsuarios.add(new Usuario(
                        rs.getString("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("email"),
                        rs.getString("telefono")
                ));
            }
        } catch (SQLException e) {
            throw new BibliotecaException("Error de base de datos al listar usuarios: " + e.getMessage(), e);
        }
        return listaUsuarios;
    }

    public boolean eliminarUsuario(String dni) throws BibliotecaException {
        String sql = "DELETE FROM Usuarios WHERE dni = ?";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dni);
            int filas = pstmt.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            throw new BibliotecaException("Error de base de datos al eliminar usuario: " + e.getMessage(), e);
        }
    }
}
