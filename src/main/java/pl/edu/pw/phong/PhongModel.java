package pl.edu.pw.phong;

import pl.edu.pw.geometry.Point3D;
import pl.edu.pw.geometry.Sphere;

public class PhongModel {

    private final int width;
    private final int height;
    private final Sphere sphere;
    private final Point3D observer;

    public PhongModel(int width, int height, Sphere sphere, Point3D observer) {
        this.width = width;
        this.height = height;
        this.sphere = sphere;
        this.observer = observer;
    }

    /**
     * Returns double[height][width] with light intensity in range 0-1
     */
    public double[][] phong(PhongParameters parameters, Point3D light, int threadsAmount) throws InterruptedException {
        double[][] result = new double[height][width];
        Thread[] threads = new Thread[threadsAmount];

        for (int i = 0; i < threadsAmount; i++) {
            Runnable runnable = phongModelRunnable(parameters, light, threadsAmount, i, result);
            threads[i] = new Thread(runnable);
            threads[i].setDaemon(true);
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        return result;
    }

    private PhongModelRunnable phongModelRunnable(PhongParameters parameters, Point3D light,
                                                  int threadsAmount, int threadNumber, double[][] result) {
        return new PhongModelRunnable(
                width,
                height,
                sphere,
                observer,
                light,
                parameters,
                result,
                threadsAmount,
                threadNumber);
    }


}
