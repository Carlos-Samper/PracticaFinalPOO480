module org.example.proyectofinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.media;
    requires javafx.web;


    opens org.example.proyectofinal480.Aplicacion to javafx.graphics, javafx.fxml;
    opens org.example.proyectofinal480.Controladores to javafx.fxml;
    exports org.example.proyectofinal480.Aplicacion;
    exports org.example.proyectofinal480.Controladores;
}
