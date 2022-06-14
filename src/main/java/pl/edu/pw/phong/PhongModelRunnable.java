package pl.edu.pw.phong;

import pl.edu.pw.geometry.Point3D;
import pl.edu.pw.geometry.Sphere;
import pl.edu.pw.geometry.Vector3D;

public class PhongModelRunnable implements Runnable {

    private final static double MAX_INTENSITY = 3.0;

    private final int width;
    private final int height;
    private final Sphere sphere;
    private final Point3D observer;
    private final Point3D light;

    private final PhongParameters parameters;
    private final double[][] result;

    private final int threadsAmount;
    private final int threadNumber;

    public PhongModelRunnable(int width, int height, Sphere sphere, Point3D observer, Point3D light,
                              PhongParameters parameters, double[][] result, int threadsAmount, int threadNumber) {
        this.width = width;
        this.height = height;
        this.sphere = sphere;
        this.observer = observer;
        this.light = light;
        this.parameters = parameters;
        this.result = result;
        this.threadsAmount = threadsAmount;
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        phong();
    }

    private void phong() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                if ((i + j) % threadsAmount == threadNumber) {
                    result[i][j] = calculatePixel(j, i);
                }

            }
        }

        // PODZIAL WIERSZOWY
//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++) {
//
//                if (i % threadsAmount == threadNumber) {
//                    result[i][j] = calculatePixel(j, i);
//                }
//
//            }
//        }

        // PODZIAL BLOKOWY WIERSZOWY
//        int start = (int) ((double) height / (double) threadsAmount * (double) threadNumber);
//        int stop = (int) ((double) height / (double) threadsAmount * (double) (threadNumber + 1));
//
//        for (int i = start; i < stop; i++) {
//            for (int j = 0; j < width; j++) {
//                result[i][j] = calculatePixel(j, i);
//            }
//        }
    }

    private double calculatePixel(int x, int y) {
        try {
            Point3D spherePoint = sphere.spherePoint(x, y);
            Vector3D L = vectorL(spherePoint);
            Vector3D N = vectorN(spherePoint);
            Vector3D R = vectorR(L, N);
            Vector3D V = vectorV(spherePoint);

            return calculatePixel(L, N, R, V);
        } catch (IllegalArgumentException e) {
            return 0;
        }
    }

    private double calculatePixel(Vector3D L, Vector3D N, Vector3D R, Vector3D V) {
        double ambient = parameters.getKa() * parameters.getIa();
        double diffuse = parameters.getKd() * parameters.getId() * L.dotProduct(N);
        double RdotV = R.dotProduct(V);
        double specular = parameters.getKs() * parameters.getIs() * Math.pow(RdotV < 0 ? 0 : RdotV, parameters.getN());

        ambient = ambient < 0 ? 0 : ambient;
        diffuse = diffuse < 0 ? 0 : diffuse;
        specular = specular < 0 ? 0 : specular;

        return (ambient + diffuse + specular) / MAX_INTENSITY;
    }

    private Vector3D vectorL(Point3D spherePoint) {
        return new Vector3D(spherePoint, light).normalize();
    }

    private Vector3D vectorN(Point3D spherePoint) {
        return new Vector3D(sphere.getCenter(), spherePoint).normalize();
    }

    private Vector3D vectorR(Vector3D L, Vector3D N) {
        Vector3D D = L.opposite();

        return D.subtract(N.multiple(2 * D.dotProduct(N))).normalize();
    }

    private Vector3D vectorV(Point3D spherePoint) {
        return new Vector3D(spherePoint, observer).normalize();
    }

}
