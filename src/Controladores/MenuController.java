package Controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController {

    @FXML
    private Button btnCatalogo;
    @FXML
    private Button btnUsuarios;
    @FXML
    private Button btnPrestamos;
    @FXML
    private Button btnHistorial;

    // Se ejecuta cuando se inicializa el controlador
    @FXML
    public void initialize() {
        // Aquí puedes inicializar componentes o cargar datos al inicio
        System.out.println("Controlador de menú inicializado.");
    }

    // Métodos para manejar las acciones de los botones
    @FXML
    private void handleBtnCatalogoAction(ActionEvent event) {
        System.out.println("Botón Catálogo pulsado.");
        // Aquí iría la lógica para abrir la vista del catálogo
    }

    @FXML
    private void handleBtnUsuarioAction(ActionEvent event) {
        System.out.println("Botón Usuarios pulsado.");
        // Aquí iría la lógica para abrir la vista de usuarios
    }

    @FXML
    private void handleBtnPrestamosAction(ActionEvent event) {
        System.out.println("Botón Préstamos pulsado.");
        // Aquí iría la lógica para abrir la vista de préstamos
    }

    @FXML
    private void handleBtnHistorialAction(ActionEvent event) {
        System.out.println("Botón Historial pulsado.");
        // Aquí iría la lógica para abrir la vista del historial
    }
}