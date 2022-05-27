package pl.edu.pw.phong;

public class PhongParameters {

    private double ka;
    private double kd;
    private double ks;
    private double ia;
    private double id;
    private double is;
    private double n;

    public PhongParameters(double ka, double kd, double ks, double ia, double id, double is, double n) {
        this.ka = ka;
        this.kd = kd;
        this.ks = ks;
        this.ia = ia;
        this.id = id;
        this.is = is;
        this.n = n;
    }

    public PhongParameters() {
    }

    public double getKa() {
        return ka;
    }

    public void setKa(double ka) {
        this.ka = ka;
    }

    public double getKd() {
        return kd;
    }

    public void setKd(double kd) {
        this.kd = kd;
    }

    public double getKs() {
        return ks;
    }

    public void setKs(double ks) {
        this.ks = ks;
    }

    public double getIa() {
        return ia;
    }

    public void setIa(double ia) {
        this.ia = ia;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public double getIs() {
        return is;
    }

    public void setIs(double is) {
        this.is = is;
    }

    public double getN() {
        return n;
    }

    public void setN(double n) {
        this.n = n;
    }
}
