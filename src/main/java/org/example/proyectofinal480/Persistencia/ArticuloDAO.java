package org.example.proyectofinal480.Persistencia;

import org.example.proyectofinal480.Modelo.*;
import org.example.proyectofinal480.Modelo.Abstractos.*;
import org.example.proyectofinal480.Modelo.Concretos.*;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDAO {

    public boolean registrarArticulo(Articulo articulo) {
        String sql = "INSERT INTO Articulos (tipo_articulo, titulo, disponible, autor, isbn, issn, numero, director, duracion_min) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(2, articulo.getTitulo());
            pstmt.setBoolean(3, true);

            pstmt.setNull(4, Types.VARCHAR);
            pstmt.setNull(5, Types.VARCHAR);
            pstmt.setNull(6, Types.VARCHAR);
            pstmt.setNull(7, Types.INTEGER);
            pstmt.setNull(8, Types.VARCHAR);
            pstmt.setNull(9, Types.INTEGER);

            if (articulo instanceof Libro) {
                Libro l = (Libro) articulo;
                pstmt.setString(1, "LIBRO");
                pstmt.setString(4, l.getAutor());
                pstmt.setString(5, l.getIsbn());
            } else if (articulo instanceof Revista) {
                Revista r = (Revista) articulo;
                pstmt.setString(1, "REVISTA");
                pstmt.setString(6, r.getIssn());
                pstmt.setInt(7, r.getNumero());
            } else if (articulo instanceof DVD) {
                DVD d = (DVD) articulo;
                pstmt.setString(1, "DVD");
                pstmt.setString(8, d.getDirector());
                pstmt.setInt(9, d.getDuracion());
            }

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Articulo> listarTodos() {
        List<Articulo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Articulos";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Articulo art = null;
                int id = rs.getInt("id");
                String tipo = rs.getString("tipo_articulo");
                String titulo = rs.getString("titulo");

                switch (tipo) {
                    case "LIBRO":
                        art = new Libro(id, titulo, rs.getString("autor"), rs.getString("isbn"));
                        break;
                    case "REVISTA":
                        art = new Revista(id, titulo, rs.getString("issn"), rs.getInt("numero"));
                        break;
                    case "DVD":
                        art = new DVD(id, titulo, rs.getString("director"), rs.getInt("duracion_min"));
                        break;
                }

                if (art != null) {
                    art.setDisponible(rs.getBoolean("disponible"));
                    art.setPrestadoADni(rs.getString("prestado_a_dni"));

                    Date fechaSql = rs.getDate("fecha_devolucion");
                    if (fechaSql != null) {
                        art.setFechaDevolucion(fechaSql.toLocalDate());
                    }
                    lista.add(art);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean actualizarEstado(Articulo articulo) {
        String sql = "UPDATE Articulos SET disponible = ?, prestado_a_dni = ?, fecha_devolucion = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBoolean(1, articulo.isDisponible());

            if (articulo.getPrestadoADni() != null) {
                pstmt.setString(2, articulo.getPrestadoADni());
            } else {
                pstmt.setNull(2, Types.VARCHAR);
            }

            if (articulo.getFechaDevolucion() != null) {
                pstmt.setDate(3, Date.valueOf(articulo.getFechaDevolucion()));
            } else {
                pstmt.setNull(3, Types.DATE);
            }

            pstmt.setInt(4, articulo.getId());
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
