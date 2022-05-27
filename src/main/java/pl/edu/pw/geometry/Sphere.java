package pl.edu.pw.geometry;

public class Sphere {

    private Point3D center;
    private double radius;

    public Sphere(Point3D center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point3D getCenter() {
        return center;
    }

    public void setCenter(Point3D center) {
        this.center = center;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Point3D spherePoint(double x, double y) {
        double tmp = Math.pow(radius, 2) - Math.pow(x - center.getX(), 2) - Math.pow(y - center.getY(), 2);
        if (tmp >= 0) {
            tmp = Math.sqrt(tmp);
            return new Point3D(x, y, Math.min(center.getZ() + tmp, center.getZ() - tmp));
        } else {
            throw new IllegalArgumentException("x, y are not in sphere");
        }
    }

}
