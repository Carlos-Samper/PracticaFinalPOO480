package Aplicacion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            URL fxmlLocation = getClass().getResource("/Resources/Vistas/vista_menu.fxml");

            if (fxmlLocation == null) {
                System.err.println("❌ ERROR: No se ha encontrado el archivo FXML.");
                System.err.println("Ruta intentada: /Resources/Vistas/vista_menu.fxml");
                System.err.println("Asegúrate de que la carpeta 'Resources' esté marcada como 'Resources Root' o esté dentro de 'src'.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();

            Scene scene = new Scene(root);


            stage.setTitle("Sistema de Gestión de Biblioteca - 480");
            stage.setScene(scene);


            stage.show();
            System.out.println("✅ Interfaz cargada correctamente.");

        } catch (Exception e) {
            System.err.println("❌ Error crítico al cargar la interfaz:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}