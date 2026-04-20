module org.example.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;
    requires javafx.web;

    opens org.proyectofinal480.Aplicacion to javafx.graphics, javafx.fxml;
    opens org.proyectofinal480.Controladores to javafx.fxml;
    opens org.proyectofinal480.Modelo to javafx.base, javafx.controls, javafx.fxml;
    opens org.proyectofinal480.Modelo.Abstractos to javafx.base, javafx.controls, javafx.fxml;
    opens org.proyectofinal480.Modelo.Concretos to javafx.base, javafx.controls, javafx.fxml;
    exports org.proyectofinal480.Aplicacion;
    exports org.proyectofinal480.Controladores;
    exports org.proyectofinal480.Excepciones;
    exports org.proyectofinal480.Logica;
    opens org.proyectofinal480 to javafx.base, javafx.controls, javafx.fxml;
}
