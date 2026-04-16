package Persistencia;

import Modelo.Concretos.Transaccion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

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
}