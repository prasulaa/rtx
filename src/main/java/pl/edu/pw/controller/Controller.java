package pl.edu.pw.controller;

import java.awt.Color;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import pl.edu.pw.geometry.Point3D;
import pl.edu.pw.geometry.Sphere;
import pl.edu.pw.phong.PhongModel;
import pl.edu.pw.phong.PhongParameters;

public class Controller {

    @FXML
    private Canvas canvas;

    @FXML
    private ChoiceBox<PhongParameters> choicebox;

    @FXML
    private Button generate;

    @FXML
    private Button left;

    @FXML
    private Button right;

    private Point3D light;

    @FXML
    private void initialize() {
        light = new Point3D(0, 0, 0);
        initializeChoiceBox();
        generateCanvas();
    }

    @FXML
    private void generateCanvas() {
//        PhongParameters parameters = new PhongParameters(ka.getValue(), kd.getValue(),
//            ks.getValue(), 1, 1, 1, n.getValue());
        int h = 500;
        int w = 500;
        Sphere sphere = new Sphere(new Point3D(h / 2, w / 2, 500), 200);
        Point3D observer = new Point3D(250, 250, 0);

        PhongModel model = new PhongModel(w, h, sphere, observer);

        double[][] result = model.phong(choicebox.getValue(), light);

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
        System.out.println(
            "Light: x = " + light.getX() + ", y = " + light.getY() + ", z = " + light.getZ());
    }

    private void initializeChoiceBox() {
        PhongParameters wall = new PhongParameters(0.6, 1, 0, 1, 1, 1, 1);
        wall.setName("wall");

        PhongParameters whiteboard = new PhongParameters(0.6, 0.6, 1, 1, 1, 1, 25);
        whiteboard.setName("whiteboard");

        PhongParameters wood = new PhongParameters(0.6, 0.8, 1, 1, 1, 1, 8);
        wood.setName("wood");

        PhongParameters plainCartboard = new PhongParameters(0.6, 1, 1, 1, 1, 1, 1);
        plainCartboard.setName("plain cartboard");

        choicebox.setItems(FXCollections.observableArrayList(wall, whiteboard, wood, plainCartboard));
        choicebox.setValue(wall);
    }

}
