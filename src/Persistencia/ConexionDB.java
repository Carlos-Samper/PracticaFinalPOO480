package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    // Ruta relativa apuntando al archivo en la raíz de nuestro proyecto
    private static final String URL = "jdbc:sqlite:biblioteca.db";

    // Variable estática para mantener una única conexión abierta (Patrón de diseño Singleton)
    private static Connection conexion = null;

    // Obtiene la conexión activa a la base de datos.
    // Si no existe o está cerrada, crea una nueva.
    // @return Objeto Connection listo para ser usado por los DAOs.
    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL);
                System.out.println("✅ Conexión a SQLite establecida correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error de SQL al intentar conectar: " + e.getMessage());
        }
        return conexion;
    }

    // Cierra la conexión a la base de datos de forma segura.
    // Es buena práctica llamarlo cuando la aplicación se vaya a cerrar.
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("🔒 Conexión a SQLite cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al cerrar la conexión: " + e.getMessage());
        }
    }
}