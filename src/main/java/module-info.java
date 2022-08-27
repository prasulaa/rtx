module pl.edu.pw {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires aparapi;

    exports pl.edu.pw to javafx.graphics;
    opens pl.edu.pw to aparapi;
    opens pl.edu.pw.phong to aparapi;
    opens pl.edu.pw.controller to javafx.fxml;
}