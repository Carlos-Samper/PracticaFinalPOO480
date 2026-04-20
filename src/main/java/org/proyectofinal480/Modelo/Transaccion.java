package org.proyectofinal480.Modelo;

import java.time.LocalDateTime;

public class Transaccion {

    private int id;
    private LocalDateTime fechaHora;
    private String tipoOperacion;
    private String dniUsuario;
    private int idArticulo;

    public Transaccion(int id, LocalDateTime fechaHora, String tipoOperacion, String dniUsuario, int idArticulo) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.tipoOperacion = tipoOperacion;
        this.dniUsuario = dniUsuario;
        this.idArticulo = idArticulo;
    }

    public Transaccion(String tipoOperacion, String dniUsuario, int idArticulo) {
        this.tipoOperacion = tipoOperacion;
        this.dniUsuario = dniUsuario;
        this.idArticulo = idArticulo;
        this.fechaHora = LocalDateTime.now();
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public LocalDateTime getFechaHora() { return fechaHora; }

    public String getTipoOperacion() { return tipoOperacion; }

    public String getDniUsuario() { return dniUsuario; }

    public int getIdArticulo() { return idArticulo; }
}
