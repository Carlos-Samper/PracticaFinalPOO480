package org.proyectofinal480.Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.proyectofinal480.Excepciones.BibliotecaException;
import org.proyectofinal480.Logica.GestorBiblioteca;
import org.proyectofinal480.Logica.Validador;
import org.proyectofinal480.Modelo.Concretos.DVD;
import org.proyectofinal480.Modelo.Concretos.Libro;
import org.proyectofinal480.Modelo.Concretos.Revista;
import org.proyectofinal480.Modelo.Usuario;
import org.proyectofinal480.Persistencia.ConexionDB;

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

    @FXML private TextField txtPrestamoIdArticulo;
    @FXML private TextField txtPrestamoDni;

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
            String titulo = txtLibroTitulo.getText().trim();
            String autor = txtLibroAutor.getText().trim();
            String isbn = txtLibroIsbn.getText().trim();

            Validador.validarNoVacio(titulo, "Titulo");
            Validador.validarNoVacio(autor, "Autor");
            Validador.validarIsbn(isbn);

            int newId = (int) (System.currentTimeMillis() % 100000);
            Libro libro = new Libro(newId, titulo, autor, isbn);
            gestorBiblioteca.registrarArticulo(libro);

            cerrarVentana(event);
            if (onRegistroExitoso != null) onRegistroExitoso.run();
        } catch (BibliotecaException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    private void handleGuardarRevista(ActionEvent event) {
        try {
            String titulo = txtRevistaTitulo.getText().trim();
            String issn = txtRevistaIssn.getText().trim();
            String numStr = txtRevistaNumero.getText().trim();

            Validador.validarNoVacio(titulo, "Titulo");
            Validador.validarIssn(issn);
            Validador.validarNumeroPositivo(numStr, "Numero");

            int numero = Integer.parseInt(numStr);
            int newId = (int) (System.currentTimeMillis() % 100000);
            Revista revista = new Revista(newId, titulo, issn, numero);
            gestorBiblioteca.registrarArticulo(revista);

            cerrarVentana(event);
            if (onRegistroExitoso != null) onRegistroExitoso.run();
        } catch (BibliotecaException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    private void handleGuardarDVD(ActionEvent event) {
        try {
            String titulo = txtDvdTitulo.getText().trim();
            String director = txtDvdDirector.getText().trim();
            String duracionStr = txtDvdDuracion.getText().trim();

            Validador.validarNoVacio(titulo, "Titulo");
            Validador.validarNoVacio(director, "Director");
            Validador.validarNumeroPositivo(duracionStr, "Duracion");

            int duracion = Integer.parseInt(duracionStr);
            int newId = (int) (System.currentTimeMillis() % 100000);
            DVD dvd = new DVD(newId, titulo, director, duracion);
            gestorBiblioteca.registrarArticulo(dvd);

            cerrarVentana(event);
            if (onRegistroExitoso != null) onRegistroExitoso.run();
        } catch (BibliotecaException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    private void handleGuardarUsuario(ActionEvent event) {
        try {
            String dni = txtUsuarioDni.getText().trim();
            String nombre = txtUsuarioNombre.getText().trim();
            String apellidos = txtUsuarioApellidos.getText().trim();
            String email = txtUsuarioEmail.getText().trim();
            String telefono = txtUsuarioTelefono.getText().trim();

            Validador.validarDni(dni);
            Validador.validarNoVacio(nombre, "Nombre");
            Validador.validarNoVacio(apellidos, "Apellidos");
            Validador.validarEmail(email);
            Validador.validarTelefono(telefono);

            Usuario usuario = new Usuario(dni, nombre, apellidos, email, telefono);
            gestorBiblioteca.registrarUsuario(usuario);

            cerrarVentana(event);
            if (onRegistroExitoso != null) onRegistroExitoso.run();
        } catch (BibliotecaException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    private void handleGuardarPrestamo(ActionEvent event) {
        try {
            String idStr = txtPrestamoIdArticulo.getText().trim();
            String dni = txtPrestamoDni.getText().trim();

            Validador.validarNoVacio(idStr, "ID Articulo");
            Validador.validarDni(dni);
            Validador.validarNumeroPositivo(idStr, "ID Articulo");

            gestorBiblioteca.prestarArticulo(Integer.parseInt(idStr), dni);
            cerrarVentana(event);
            if (onRegistroExitoso != null) onRegistroExitoso.run();
        } catch (BibliotecaException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    private void handleGuardarDevolucion(ActionEvent event) {
        try {
            String idStr = txtDevolucionIdArticulo.getText().trim();

            Validador.validarNoVacio(idStr, "ID Articulo");
            Validador.validarNumeroPositivo(idStr, "ID Articulo");

            double recargo = gestorBiblioteca.devolverArticulo(Integer.parseInt(idStr));

            cerrarVentana(event);
            if (onRegistroExitoso != null) onRegistroExitoso.run();

            if (recargo > 0) {
                Alert alerta = new Alert(Alert.AlertType.WARNING);
                alerta.setTitle("Devolucion con retraso");
                alerta.setHeaderText(null);
                alerta.setContentText("El articulo se devuelve con retraso. Recargo a abonar: " + String.format("%.2f", recargo) + " EUR.");
                alerta.showAndWait();
            }
        } catch (BibliotecaException e) {
            mostrarError(e.getMessage());
        }
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        cerrarVentana(event);
    }

    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ConexionDB.cerrarConexion();
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
