module pl.edu.pw {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;

    exports pl.edu.pw to javafx.graphics;
    opens pl.edu.pw.controller to javafx.fxml;
}