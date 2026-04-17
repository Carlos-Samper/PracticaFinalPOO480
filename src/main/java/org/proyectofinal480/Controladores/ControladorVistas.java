package org.proyectofinal480.Controladores;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.proyectofinal480.Logica.GestorBiblioteca;
import org.proyectofinal480.Modelo.Abstractos.Articulo;
import org.proyectofinal480.Modelo.Concretos.DVD;
import org.proyectofinal480.Modelo.Concretos.Libro;
import org.proyectofinal480.Modelo.Concretos.Revista;
import org.proyectofinal480.Modelo.Transaccion;
import org.proyectofinal480.Modelo.Usuario;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class ControladorVistas {

    @FXML private Button btnLibro;
    @FXML private Button btnRevista;
    @FXML private Button btnDVD;
    @FXML private Button btnUsuario;
    @FXML private Button btnHistorial;
    @FXML private Button btnSalir;

    @FXML private Button btnRegistro;
    @FXML private TextField txtBuscar;
    @FXML private Button btnTodo;
    @FXML private Button btnDisponible;
    @FXML private Button btnPrestado;
    @FXML private Button btnPrestar;
    @FXML private Button btnDevolver;

    @FXML private TableView<Libro> tvLibros;
    @FXML private TableColumn<Libro, Integer> colLibroId;
    @FXML private TableColumn<Libro, String> colLibroTitulo;
    @FXML private TableColumn<Libro, String> colLibroAutor;
    @FXML private TableColumn<Libro, String> colLibroIsbn;
    @FXML private TableColumn<Libro, Boolean> colLibroEstado;

    @FXML private TableView<Revista> tvRevistas;
    @FXML private TableColumn<Revista, Integer> colRevistaId;
    @FXML private TableColumn<Revista, String> colRevistaTitulo;
    @FXML private TableColumn<Revista, String> colRevistaIssn;
    @FXML private TableColumn<Revista, Integer> colRevistaNumero;
    @FXML private TableColumn<Revista, Boolean> colRevistaEstado;

    @FXML private TableView<DVD> tvDVDs;
    @FXML private TableColumn<DVD, Integer> colDvdId;
    @FXML private TableColumn<DVD, String> colDvdTitulo;
    @FXML private TableColumn<DVD, String> colDvdDirector;
    @FXML private TableColumn<DVD, Integer> colDvdDuracion;
    @FXML private TableColumn<DVD, Boolean> colDvdEstado;

    @FXML private TableView<Usuario> tvUsuarios;
    @FXML private TableColumn<Usuario, String> colUsuarioNombre;
    @FXML private TableColumn<Usuario, String> colUsuarioApellidos;
    @FXML private TableColumn<Usuario, String> colUsuarioDni;
    @FXML private TableColumn<Usuario, String> colUsuarioEmail;
    @FXML private TableColumn<Usuario, String> colUsuarioTelefono;

    @FXML private TableView<Transaccion> tvHistorial;
    @FXML private TableColumn<Transaccion, Integer> colHistorialId;
    @FXML private TableColumn<Transaccion, String> colHistorialFecha;
    @FXML private TableColumn<Transaccion, String> colHistorialTipo;
    @FXML private TableColumn<Transaccion, String> colHistorialDni;
    @FXML private TableColumn<Transaccion, Integer> colHistorialArticuloId;
    @FXML private TableColumn<Transaccion, String> colHistorialNombreUsuario;

    private final GestorBiblioteca gestorBiblioteca = new GestorBiblioteca();
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private String filtroActivo = "todos";

    @FXML
    public void initialize() {
        if (tvLibros != null) {
            colLibroId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colLibroTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            colLibroAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
            colLibroIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
            colLibroEstado.setCellValueFactory(new PropertyValueFactory<>("disponible"));
            colLibroEstado.setCellFactory(col -> crearCeldaEstado());
            if (txtBuscar != null)
                txtBuscar.textProperty().addListener((obs, o, n) -> aplicarFiltrosLibros());
            aplicarFiltrosLibros();
        }

        if (tvRevistas != null) {
            colRevistaId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colRevistaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            colRevistaIssn.setCellValueFactory(new PropertyValueFactory<>("issn"));
            colRevistaNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
            colRevistaEstado.setCellValueFactory(new PropertyValueFactory<>("disponible"));
            colRevistaEstado.setCellFactory(col -> crearCeldaEstado());
            if (txtBuscar != null)
                txtBuscar.textProperty().addListener((obs, o, n) -> aplicarFiltrosRevistas());
            aplicarFiltrosRevistas();
        }

        if (tvDVDs != null) {
            colDvdId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colDvdTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
            colDvdDirector.setCellValueFactory(new PropertyValueFactory<>("director"));
            colDvdDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));
            colDvdEstado.setCellValueFactory(new PropertyValueFactory<>("disponible"));
            colDvdEstado.setCellFactory(col -> crearCeldaEstado());
            if (txtBuscar != null)
                txtBuscar.textProperty().addListener((obs, o, n) -> aplicarFiltrosDVDs());
            aplicarFiltrosDVDs();
        }

        if (tvUsuarios != null) {
            colUsuarioNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colUsuarioApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
            colUsuarioDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
            colUsuarioEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colUsuarioTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
            if (txtBuscar != null)
                txtBuscar.textProperty().addListener((obs, o, n) -> aplicarFiltrosUsuarios());
            aplicarFiltrosUsuarios();
        }

        if (tvHistorial != null) {
            colHistorialId.setCellValueFactory(new PropertyValueFactory<>("id"));
            colHistorialFecha.setCellValueFactory(data ->
                    new SimpleStringProperty(data.getValue().getFechaHora().format(FORMATO_FECHA)));
            colHistorialTipo.setCellValueFactory(new PropertyValueFactory<>("tipoOperacion"));
            colHistorialDni.setCellValueFactory(new PropertyValueFactory<>("dniUsuario"));
            colHistorialArticuloId.setCellValueFactory(new PropertyValueFactory<>("idArticulo"));
            if (colHistorialNombreUsuario != null) {
                colHistorialNombreUsuario.setCellValueFactory(data -> {
                    Usuario u = gestorBiblioteca.obtenerUsuarioPorDni(data.getValue().getDniUsuario());
                    return new SimpleStringProperty(u != null ? u.getNombreCompleto() : data.getValue().getDniUsuario());
                });
            }
            if (txtBuscar != null)
                txtBuscar.textProperty().addListener((obs, o, n) -> aplicarFiltrosHistorial());
            aplicarFiltrosHistorial();
        }
    }

    private <T> TableCell<T, Boolean> crearCeldaEstado() {
        return new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else if (item) {
                    setText("Disponible");
                    setTextFill(Color.web("#16a34a"));
                    setStyle("-fx-font-weight: bold;");
                } else {
                    setText("Prestado");
                    setTextFill(Color.web("#9c2007"));
                    setStyle("-fx-font-weight: bold;");
                }
            }
        };
    }


    private void aplicarFiltrosLibros() {
        String q = txtBuscar != null ? txtBuscar.getText().toLowerCase() : "";
        tvLibros.setItems(FXCollections.observableArrayList(
            gestorBiblioteca.obtenerTodosLosArticulos().stream()
                .filter(a -> a instanceof Libro)
                .filter(a -> filtroActivo.equals("todos") ||
                             (filtroActivo.equals("disponibles") && a.isDisponible()) ||
                             (filtroActivo.equals("prestados") && !a.isDisponible()))
                .filter(a -> q.isEmpty() || a.getTitulo().toLowerCase().contains(q))
                .map(a -> (Libro) a)
                .collect(Collectors.toList())));
    }

    private void aplicarFiltrosRevistas() {
        String q = txtBuscar != null ? txtBuscar.getText().toLowerCase() : "";
        tvRevistas.setItems(FXCollections.observableArrayList(
            gestorBiblioteca.obtenerTodosLosArticulos().stream()
                .filter(a -> a instanceof Revista)
                .filter(a -> filtroActivo.equals("todos") ||
                             (filtroActivo.equals("disponibles") && a.isDisponible()) ||
                             (filtroActivo.equals("prestados") && !a.isDisponible()))
                .filter(a -> q.isEmpty() || a.getTitulo().toLowerCase().contains(q))
                .map(a -> (Revista) a)
                .collect(Collectors.toList())));
    }

    private void aplicarFiltrosDVDs() {
        String q = txtBuscar != null ? txtBuscar.getText().toLowerCase() : "";
        tvDVDs.setItems(FXCollections.observableArrayList(
            gestorBiblioteca.obtenerTodosLosArticulos().stream()
                .filter(a -> a instanceof DVD)
                .filter(a -> filtroActivo.equals("todos") ||
                             (filtroActivo.equals("disponibles") && a.isDisponible()) ||
                             (filtroActivo.equals("prestados") && !a.isDisponible()))
                .filter(a -> q.isEmpty() || a.getTitulo().toLowerCase().contains(q))
                .map(a -> (DVD) a)
                .collect(Collectors.toList())));
    }

    private void aplicarFiltrosUsuarios() {
        String q = txtBuscar != null ? txtBuscar.getText().toLowerCase() : "";
        tvUsuarios.setItems(FXCollections.observableArrayList(
            gestorBiblioteca.obtenerTodosLosUsuarios().stream()
                .filter(u -> q.isEmpty() || u.getDni().toLowerCase().contains(q))
                .collect(Collectors.toList())));
    }

    private void aplicarFiltrosHistorial() {
        String q = txtBuscar != null ? txtBuscar.getText().toLowerCase() : "";
        tvHistorial.setItems(FXCollections.observableArrayList(
            gestorBiblioteca.obtenerTodasLasTransacciones().stream()
                .filter(t -> {
                    if (q.isEmpty()) return true;
                    Usuario u = gestorBiblioteca.obtenerUsuarioPorDni(t.getDniUsuario());
                    String nombre = u != null ? u.getNombreCompleto().toLowerCase() : t.getDniUsuario().toLowerCase();
                    return nombre.contains(q);
                })
                .collect(Collectors.toList())));
    }

    private void cargarLibros()   { aplicarFiltrosLibros(); }
    private void cargarRevistas() { aplicarFiltrosRevistas(); }
    private void cargarDVDs()     { aplicarFiltrosDVDs(); }
    private void cargarUsuarios() { aplicarFiltrosUsuarios(); }
    private void cargarHistorial(){ aplicarFiltrosHistorial(); }

    private void recargarTablaActiva() {
        if (tvLibros != null) cargarLibros();
        else if (tvRevistas != null) cargarRevistas();
        else if (tvDVDs != null) cargarDVDs();
    }

    @FXML
    private void handleNavegacion(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String vista = "";

        if (btn == btnLibro) vista = "vista_libros.fxml";
        else if (btn == btnRevista) vista = "vista_revista.fxml";
        else if (btn == btnDVD) vista = "vista_DVD.fxml";
        else if (btn == btnUsuario) vista = "vista_usuarios.fxml";
        else if (btn == btnHistorial) vista = "vista_historial.fxml";

        if (!vista.isEmpty()) {
            cambiarVista(event, "/org/proyectofinal480/" + vista);
        }
    }

    private void cambiarVista(ActionEvent event, String fxmlPath) {
        try {
            var url = getClass().getResource(fxmlPath);
            if (url == null) throw new IllegalStateException("Recurso no encontrado: " + fxmlPath);
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSalir(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleNuevoRegistro(ActionEvent event) {
        String fxmlFormulario = "";
        Runnable reloadAction = null;
        String tituloVentana = "";

        if (tvLibros != null) {
            fxmlFormulario = "/org/proyectofinal480/vista_formulario_libro.fxml";
            tituloVentana = "Nuevo Libro";
            reloadAction = this::cargarLibros;
        } else if (tvRevistas != null) {
            fxmlFormulario = "/org/proyectofinal480/vista_formulario_revista.fxml";
            tituloVentana = "Nueva Revista";
            reloadAction = this::cargarRevistas;
        } else if (tvDVDs != null) {
            fxmlFormulario = "/org/proyectofinal480/vista_formulario_DVD.fxml";
            tituloVentana = "Nuevo DVD";
            reloadAction = this::cargarDVDs;
        } else if (tvUsuarios != null) {
            fxmlFormulario = "/org/proyectofinal480/vista_formulario_usuario.fxml";
            tituloVentana = "Nuevo Usuario";
            reloadAction = this::cargarUsuarios;
        }

        if (!fxmlFormulario.isEmpty()) {
            abrirFormulario(fxmlFormulario, tituloVentana, reloadAction, -1);
        }
    }

    private void abrirFormulario(String fxmlPath, String titulo, Runnable onSuccess, int idArticulo) {
        try {
            var url = getClass().getResource(fxmlPath);
            if (url == null) throw new IllegalStateException("Recurso no encontrado: " + fxmlPath);
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            ControladorFormularios controller = loader.getController();
            if (controller != null) {
                controller.setOnRegistroExitoso(onSuccess);
                if (idArticulo > 0) controller.setIdArticulo(idArticulo);
            }

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void actualizarFiltroUI(Button seleccionado) {
        if (btnTodo == null) return;
        String activo   = "-fx-background-color: #9c2007; -fx-background-radius: 8;";
        String inactivo = "-fx-background-color: transparent; -fx-background-radius: 8;";

        btnTodo.setStyle(seleccionado == btnTodo ? activo : inactivo);
        btnTodo.setTextFill(seleccionado == btnTodo ? Color.WHITE : Color.web("#92400e"));

        btnDisponible.setStyle(seleccionado == btnDisponible ? activo : inactivo);
        btnDisponible.setTextFill(seleccionado == btnDisponible ? Color.WHITE : Color.web("#92400e"));

        btnPrestado.setStyle(seleccionado == btnPrestado ? activo : inactivo);
        btnPrestado.setTextFill(seleccionado == btnPrestado ? Color.WHITE : Color.web("#92400e"));
    }

    @FXML
    private void handleFiltroTodo(ActionEvent event) {
        filtroActivo = "todos";
        actualizarFiltroUI(btnTodo);
        if (tvLibros != null) aplicarFiltrosLibros();
        if (tvRevistas != null) aplicarFiltrosRevistas();
        if (tvDVDs != null) aplicarFiltrosDVDs();
    }

    @FXML
    private void handleFiltroDisponible(ActionEvent event) {
        filtroActivo = "disponibles";
        actualizarFiltroUI(btnDisponible);
        if (tvLibros != null) aplicarFiltrosLibros();
        if (tvRevistas != null) aplicarFiltrosRevistas();
        if (tvDVDs != null) aplicarFiltrosDVDs();
    }

    @FXML
    private void handleFiltroPrestado(ActionEvent event) {
        filtroActivo = "prestados";
        actualizarFiltroUI(btnPrestado);
        if (tvLibros != null) aplicarFiltrosLibros();
        if (tvRevistas != null) aplicarFiltrosRevistas();
        if (tvDVDs != null) aplicarFiltrosDVDs();
    }

    @FXML
    private void handlePrestar(ActionEvent event) {
        Articulo seleccionado = obtenerArticuloSeleccionado();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Selecciona un artículo de la tabla primero.");
            return;
        }
        if (!seleccionado.isDisponible()) {
            mostrarAlerta(Alert.AlertType.WARNING, "No disponible", "Este artículo ya está prestado a otro usuario.");
            return;
        }
        abrirFormulario(
                "/org/proyectofinal480/vista_formulario_prestamo.fxml",
            "Préstamo de Artículo",
            this::recargarTablaActiva,
            seleccionado.getId()
        );
    }

    @FXML
    private void handleDevolver(ActionEvent event) {
        Articulo seleccionado = obtenerArticuloSeleccionado();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Selecciona un artículo de la tabla primero.");
            return;
        }
        if (seleccionado.isDisponible()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Ya disponible", "Este artículo ya está disponible y no ha sido prestado.");
            return;
        }
        abrirFormulario(
                "/org/proyectofinal480/vista_formulario_devolucion.fxml",
            "Devolución de Artículo",
            this::recargarTablaActiva,
            seleccionado.getId()
        );
    }

    private Articulo obtenerArticuloSeleccionado() {
        if (tvLibros != null) return tvLibros.getSelectionModel().getSelectedItem();
        if (tvRevistas != null) return tvRevistas.getSelectionModel().getSelectedItem();
        if (tvDVDs != null) return tvDVDs.getSelectionModel().getSelectedItem();
        return null;
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
