package pl.edu.pw.controller;

import java.awt.Color;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import pl.edu.pw.geometry.Point3D;
import pl.edu.pw.geometry.Sphere;
import pl.edu.pw.phong.PhongModel;
import pl.edu.pw.phong.PhongParameters;

public class Controller {

    @FXML
    private Canvas canvas;

    @FXML
    private Slider ka;

    @FXML
    private Slider kd;

    @FXML
    private Slider ks;

    @FXML
    private Spinner<Integer> n;

    @FXML
    private Spinner<Integer> threadsN;

    private Point3D light;

    @FXML
    private void initialize() {
        light = new Point3D(0, 0, 0);
        initializeSpinners();
        generateCanvas();
    }

    @FXML
    private void generateCanvas() {
        PhongParameters parameters = new PhongParameters(ka.getValue(), kd.getValue(),
            ks.getValue(), 1, 1, 1, n.getValue());

        int h = (int) canvas.getHeight();
        int w = (int) canvas.getWidth();
        Sphere sphere = new Sphere(new Point3D(h / 2, w / 2, 500), 200);
        Point3D observer = new Point3D(h/2, w/2, 0);

        PhongModel model = new PhongModel(w, h, sphere, observer);

        double[][] result = model.phong(parameters, light);

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int intensity = (int) (result[i][j] * 255);
                canvas.getGraphicsContext2D().getPixelWriter()
                    .setArgb(j, i, new Color(intensity / 3, intensity / 3, intensity).getRGB());
            }
        }
    }

    @FXML
    private void rotateLightLeft() {
        rotateLightAroundAxisZ(-0.3);
        generateCanvas();
    }

    @FXML
    private void rotateLightRight() {
        rotateLightAroundAxisZ(0.3);
        generateCanvas();
    }

    private void rotateLightAroundAxisZ(double alpha) {
        double x = this.light.getX() - 250;
        double y = this.light.getY() - 250;

        this.light.setX((x * Math.cos(alpha) - y * Math.sin(alpha)) + 250);
        this.light.setY((x * Math.sin(alpha) + y * Math.cos(alpha)) + 250);
//        System.out.println(
//            "Light: x = " + light.getX() + ", y = " + light.getY() + ", z = " + light.getZ());
    }

    private void initializeSpinners() {
        n.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999, 1));
        threadsN.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
    }

}
