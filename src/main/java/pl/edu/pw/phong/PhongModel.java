package pl.edu.pw.phong;

import pl.edu.pw.geometry.Point3D;
import pl.edu.pw.geometry.Sphere;
import pl.edu.pw.geometry.Vector3D;

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

    /** Returns double[height][width] */
    public double[][] phong(PhongParameters parameters, Point3D light) {
        double[][] result = new double[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                try {
                    Point3D spherePoint = sphere.spherePoint(j, i);
                    Vector3D L = vectorL(spherePoint, light);
                    Vector3D N = vectorN(spherePoint);
                    Vector3D R = vectorR(L, N);
                    Vector3D V = vectorV(spherePoint);

                    result[i][j] = calculatePixel(parameters, L, N, R, V);
                } catch (IllegalArgumentException e) {
                    result[i][j] = 0;
                }
            }
        }

        return result;
    }

    private double calculatePixel(PhongParameters parameters, Vector3D L, Vector3D N, Vector3D R, Vector3D V) {
        double ambient = parameters.getKa() * parameters.getIa();
        double diffuse = parameters.getKd() * parameters.getId() * L.dotProduct(N);
        double RdotV = R.dotProduct(V);
        double specular = parameters.getKs() * parameters.getIs() * Math.pow(RdotV < 0 ? 0: RdotV, parameters.getN());

        ambient = ambient < 0 ? 0 : ambient;
        diffuse = diffuse < 0 ? 0 : diffuse;
        specular = specular < 0 ? 0: specular;

        return ambient + diffuse + specular;
    }

    private Vector3D vectorL(Point3D spherePoint, Point3D light) {
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
