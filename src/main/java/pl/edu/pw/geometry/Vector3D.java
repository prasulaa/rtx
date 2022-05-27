package pl.edu.pw.geometry;

public class Vector3D {

    private double x;
    private double y;
    private double z;

    public Vector3D(Point3D A, Point3D B) {
        x = B.getX() - A.getX();
        y = B.getY() - A.getY();
        z = B.getZ() - A.getZ();
    }

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Vector3D opposite() {
        return new Vector3D(-1*x, -1*y, -1*z);
    }

    public Vector3D subtract(Vector3D v) {
        return new Vector3D(x - v.getX(), y - v.getY(), z - v.getZ());
    }

    public Vector3D multiple(double a) {
        return new Vector3D(a*x, a*y, a*z);
    }

    public double dotProduct(Vector3D v) {
        return x*v.getX() + y*v.getY() + z*v.getZ();
    }

    public double length() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public Vector3D normalize() {
        double length = length();

        return new Vector3D(x/length, y/length, z/length);
    }
}
