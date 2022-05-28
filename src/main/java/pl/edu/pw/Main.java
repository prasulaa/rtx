package pl.edu.pw;

import pl.edu.pw.geometry.Point3D;
import pl.edu.pw.geometry.Sphere;
import pl.edu.pw.phong.PhongModel;
import pl.edu.pw.phong.PhongParameters;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        int h = 500;
        int w = 500;
        PhongParameters parameters = new PhongParameters(1, 1, 1, 1, 1, 1, 25);
        Sphere sphere = new Sphere(new Point3D(h/2, w/2, 500), 200);
        Point3D observer = new Point3D(0, 0, 0);

        PhongModel model = new PhongModel(w, h, sphere, observer);
        Point3D light = new Point3D(0, 0, 0);

        double[][] result = model.phong(parameters, light);

        double max = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (max < result[i][j]) {
                    max = result[i][j];
                }
            }
        }

        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int intensity = (int)(result[i][j]*255);
                image.setRGB(j, i, new Color(intensity/3, intensity/3, intensity).getRGB());
            }
        }

        System.out.println(max);
        ImageIO.write(image, "png", new File("image.png"));
    }

}
