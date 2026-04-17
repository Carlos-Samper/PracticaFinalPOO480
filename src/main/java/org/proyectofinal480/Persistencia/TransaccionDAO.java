package org.proyectofinal480.Persistencia;

import org.proyectofinal480.Modelo.Transaccion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TransaccionDAO {

    public boolean registrarTransaccion(Transaccion transaccion) {
        String sql = "INSERT INTO Transacciones (tipo_operacion, dni_usuario, id_articulo, fecha_hora) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, transaccion.getTipoOperacion());
            pstmt.setString(2, transaccion.getDniUsuario());
            pstmt.setInt(3, transaccion.getIdArticulo());
            pstmt.setTimestamp(4, Timestamp.valueOf(transaccion.getFechaHora()));

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Transaccion> listarTodos() {
        List<Transaccion> transacciones = new ArrayList<>();
        String sql = "SELECT * FROM Transacciones";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
             
            while (rs.next()) {
                Transaccion transaccion = new Transaccion(
                        rs.getInt("id"),
                        rs.getTimestamp("fecha_hora").toLocalDateTime(),
                        rs.getString("tipo_operacion"),
                        rs.getString("dni_usuario"),
                        rs.getInt("id_articulo")
                );
                transacciones.add(transaccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transacciones;
    }
}