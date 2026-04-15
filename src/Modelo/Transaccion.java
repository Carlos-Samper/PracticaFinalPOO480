package Modelo;

import java.time.LocalDateTime;

public class Transaccion {
    private int id;
    private LocalDateTime fechaHora;
    private String tipoOperacion; // "PRESTAMO" o "DEVOLUCION"
    private String dniUsuario;
    private int idArticulo;

    public Transaccion(int id, LocalDateTime fechaHora, String tipoOperacion, String dniUsuario, int idArticulo) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.tipoOperacion = tipoOperacion;
        this.dniUsuario = dniUsuario;
        this.idArticulo = idArticulo;
    }

    // Constructor sin ID ni fecha (para cuando registramos una nueva transacción antes de guardarla en la DB)
    public Transaccion(String tipoOperacion, String dniUsuario, int idArticulo) {
        this.tipoOperacion = tipoOperacion;
        this.dniUsuario = dniUsuario;
        this.idArticulo = idArticulo;
        this.fechaHora = LocalDateTime.now(); // Coge la fecha y hora actual automáticamente
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getTipoOperacion() { return tipoOperacion; }
    public void setTipoOperacion(String tipoOperacion) { this.tipoOperacion = tipoOperacion; }

    public String getDniUsuario() { return dniUsuario; }
    public void setDniUsuario(String dniUsuario) { this.dniUsuario = dniUsuario; }

    public int getIdArticulo() { return idArticulo; }
    public void setIdArticulo(int idArticulo) { this.idArticulo = idArticulo; }
}
