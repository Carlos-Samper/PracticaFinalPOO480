package org.proyectofinal480.Aplicacion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AppJavaFX extends Application {

    @Override
    public void start(Stage stage) throws IOException {
            URL fxmlLocation = getClass().getResource("/org/proyectofinal480/vista_libros.fxml");

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();

            Scene scene = new Scene(root);


            stage.setTitle("Sistema de Gestion de Biblioteca - 480");
            stage.setScene(scene);


            stage.show();
            System.out.println("Interfaz cargada correctamente.");
    }

    public static void main(String[] args) {
        launch(args);
    }
}