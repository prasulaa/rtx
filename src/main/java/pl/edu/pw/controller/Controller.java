package pl.edu.pw.controller;

import com.aparapi.Kernel;
import com.aparapi.Range;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import pl.edu.pw.geometry.Point3D;
import pl.edu.pw.geometry.Sphere;
import pl.edu.pw.phong.PhongModel;
import pl.edu.pw.phong.PhongModelGPU;
import pl.edu.pw.phong.PhongParameters;

import java.awt.*;

public class Controller {

    public static int h = 700;
    public static int w = 700;

    @FXML
    private BorderPane borderPane;

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

    @FXML
    private Label time;

    private Point3D light;
    private PhongModel model;
    private Sphere sphere;
    private Point3D observer;

    @FXML
    private void initialize() {
        canvas = new Canvas(w, h);
        borderPane.setCenter(canvas);

        light = new Point3D(0, 0, 0);
        sphere = new Sphere(new Point3D(w / 2, h / 2, w), w * 0.45);
        observer = new Point3D(w / 2, h / 2, 0);

        model = new PhongModel(w, h, sphere, observer);

        initializeSpinners();
        generateCanvas();
    }

    @FXML
    private void generateCanvas() {
        try {
            PhongParameters parameters = phongParameters();

            long start = System.nanoTime();
            //double[][] result = model.phong(parameters, light, threadsN.getValue());
            float[][] result = runPhongModelGPU(parameters);
            long finish = System.nanoTime();
            double elapsedTime = ((double) finish - (double) start) / 1e6;

            time.setText(String.format("%.2f", elapsedTime));

            drawCanvas(result);
        } catch (Exception e) {
            e.printStackTrace();
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

    private float[][] runPhongModelGPU(PhongParameters parameters) {
        float[][] result = new float[h][w];

        Kernel kernel = new PhongModelGPU(sphere, observer, parameters, light, result);
        kernel.execute(Range.create2D(w, h));
        kernel.dispose();

        return result;
    }

    private PhongParameters phongParameters() {
        return new PhongParameters(
                ka.getValue(), kd.getValue(), ks.getValue(),
                1, 1, 1,
                n.getValue());
    }

    private void drawCanvas(float[][] result) {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int intensity = (int) (result[i][j] * 255);
                canvas.getGraphicsContext2D().getPixelWriter()
                        .setArgb(j, i, new Color(intensity / 3, intensity / 3, intensity).getRGB());
            }
        }
    }

    private void drawCanvas(double[][] result) {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int intensity = (int) (result[i][j] * 255);
                canvas.getGraphicsContext2D().getPixelWriter()
                        .setArgb(j, i, new Color(intensity / 3, intensity / 3, intensity).getRGB());
            }
        }
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
