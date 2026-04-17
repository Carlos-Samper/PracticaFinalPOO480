package org.proyectofinal480.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.proyectofinal480.Logica.GestorBiblioteca;
import org.proyectofinal480.Modelo.Concretos.DVD;
import org.proyectofinal480.Modelo.Concretos.Libro;
import org.proyectofinal480.Modelo.Concretos.Revista;
import org.proyectofinal480.Modelo.Usuario;

public class ControladorFormularios {

    private GestorBiblioteca gestorBiblioteca = new GestorBiblioteca();
    private Runnable onRegistroExitoso;

    @FXML private TextField txtLibroTitulo;
    @FXML private TextField txtLibroAutor;
    @FXML private TextField txtLibroIsbn;

    @FXML private TextField txtRevistaTitulo;
    @FXML private TextField txtRevistaIssn;
    @FXML private TextField txtRevistaNumero;

    @FXML private TextField txtDvdTitulo;
    @FXML private TextField txtDvdDirector;
    @FXML private TextField txtDvdDuracion;

    @FXML private TextField txtUsuarioDni;
    @FXML private TextField txtUsuarioNombre;
    @FXML private TextField txtUsuarioApellidos;
    @FXML private TextField txtUsuarioEmail;
    @FXML private TextField txtUsuarioTelefono;

    // --- CAMPOS PRÉSTAMO ---
    @FXML private TextField txtPrestamoIdArticulo;
    @FXML private TextField txtPrestamoDni;

    // --- CAMPOS DEVOLUCIÓN ---
    @FXML private TextField txtDevolucionIdArticulo;

    public void setOnRegistroExitoso(Runnable onRegistroExitoso) {
        this.onRegistroExitoso = onRegistroExitoso;
    }

    public void setIdArticulo(int id) {
        if (txtPrestamoIdArticulo != null) {
            txtPrestamoIdArticulo.setText(String.valueOf(id));
            txtPrestamoIdArticulo.setEditable(false);
        }
        if (txtDevolucionIdArticulo != null) {
            txtDevolucionIdArticulo.setText(String.valueOf(id));
            txtDevolucionIdArticulo.setEditable(false);
        }
    }

    @FXML
    private void handleGuardarLibro(ActionEvent event) {
        try {
            String titulo = txtLibroTitulo.getText();
            String autor = txtLibroAutor.getText();
            String isbn = txtLibroIsbn.getText();
            
            if(titulo.isEmpty() || autor.isEmpty() || isbn.isEmpty()) throw new Exception("Todos los campos son obligatorios.");

            int newId = (int) (System.currentTimeMillis() % 100000); // Generar un id simple
            Libro libro = new Libro(newId, titulo, autor, isbn);
            gestorBiblioteca.registrarArticulo(libro);
            
            cerrarVentana(event);
            if (onRegistroExitoso != null) onRegistroExitoso.run();
        } catch (Exception e) {
            mostrarError("Error al guardar libro: " + e.getMessage());
        }
    }

    @FXML
    private void handleGuardarRevista(ActionEvent event) {
        try {
            String titulo = txtRevistaTitulo.getText();
            String issn = txtRevistaIssn.getText();
            String numStr = txtRevistaNumero.getText();
            
            if(titulo.isEmpty() || issn.isEmpty() || numStr.isEmpty()) throw new Exception("Todos los campos son obligatorios.");

            int numero = Integer.parseInt(numStr);
            int newId = (int) (System.currentTimeMillis() % 100000);
            Revista revista = new Revista(newId, titulo, issn, numero);
            gestorBiblioteca.registrarArticulo(revista);
            
            cerrarVentana(event);
            if (onRegistroExitoso != null) onRegistroExitoso.run();
        } catch (NumberFormatException e) {
            mostrarError("El campo número debe ser un valor numérico válido.");
        } catch (Exception e) {
            mostrarError("Error al guardar revista: " + e.getMessage());
        }
    }

    @FXML
    private void handleGuardarDVD(ActionEvent event) {
        try {
            String titulo = txtDvdTitulo.getText();
            String director = txtDvdDirector.getText();
            String duracionStr = txtDvdDuracion.getText();
            
            if(titulo.isEmpty() || director.isEmpty() || duracionStr.isEmpty()) throw new Exception("Todos los campos son obligatorios.");

            int duracion = Integer.parseInt(duracionStr);
            int newId = (int) (System.currentTimeMillis() % 100000);
            DVD dvd = new DVD(newId, titulo, director, duracion);
            gestorBiblioteca.registrarArticulo(dvd);
            
            cerrarVentana(event);
            if (onRegistroExitoso != null) onRegistroExitoso.run();
        } catch (NumberFormatException e) {
            mostrarError("La duración debe ser un número válido.");
        } catch (Exception e) {
            mostrarError("Error al guardar DVD: " + e.getMessage());
        }
    }

    @FXML
    private void handleGuardarUsuario(ActionEvent event) {
        try {
            String dni = txtUsuarioDni.getText();
            String nombre = txtUsuarioNombre.getText();
            String apellidos = txtUsuarioApellidos.getText();
            String email = txtUsuarioEmail.getText();
            String telefono = txtUsuarioTelefono.getText();
            
            if(dni.isEmpty() || nombre.isEmpty() || apellidos.isEmpty()) throw new Exception("DNI, nombre y apellidos son obligatorios.");

            Usuario usuario = new Usuario(dni, nombre, apellidos, email, telefono);
            gestorBiblioteca.registrarUsuario(usuario);
            
            cerrarVentana(event);
            if (onRegistroExitoso != null) onRegistroExitoso.run();
        } catch (Exception e) {
            mostrarError("Error al guardar usuario: " + e.getMessage());
        }
    }

    @FXML
    private void handleGuardarPrestamo(ActionEvent event) {
        try {
            String idStr = txtPrestamoIdArticulo.getText();
            String dni = txtPrestamoDni.getText();
            if (idStr.isEmpty() || dni.isEmpty()) throw new Exception("Todos los campos son obligatorios.");
            gestorBiblioteca.prestarArticulo(Integer.parseInt(idStr), dni);
            cerrarVentana(event);
            if (onRegistroExitoso != null) onRegistroExitoso.run();
        } catch (NumberFormatException e) {
            mostrarError("El ID del artículo debe ser un número válido.");
        } catch (Exception e) {
            mostrarError("Error al registrar préstamo: " + e.getMessage());
        }
    }

    @FXML
    private void handleGuardarDevolucion(ActionEvent event) {
        try {
            String idStr = txtDevolucionIdArticulo.getText();
            if (idStr.isEmpty()) throw new Exception("El ID del artículo es obligatorio.");
            gestorBiblioteca.devolverArticulo(Integer.parseInt(idStr));
            cerrarVentana(event);
            if (onRegistroExitoso != null) onRegistroExitoso.run();
        } catch (NumberFormatException e) {
            mostrarError("El ID del artículo debe ser un número válido.");
        } catch (Exception e) {
            mostrarError("Error al registrar devolución: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        cerrarVentana(event);
    }

    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
