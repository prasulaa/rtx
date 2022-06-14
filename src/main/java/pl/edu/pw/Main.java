package pl.edu.pw;

import com.aparapi.Range;
import com.aparapi.Kernel;
import com.aparapi.device.Device;
import com.aparapi.device.OpenCLDevice;
import com.aparapi.internal.kernel.KernelManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.edu.pw.controller.Controller;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        try {
            Controller.h = Integer.parseInt(args[0]);
            Controller.w = Integer.parseInt(args[1]);
        } catch (ArrayIndexOutOfBoundsException ignored) {
        } catch (NumberFormatException e) {
            System.err.println("Wrong args format");
        }

        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Pane mainPane = FXMLLoader.load(getClass().getResource("/mainPane.fxml"));
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.setTitle("Ray tracer");
        stage.show();
        stage.setResizable(false);
    }

}
