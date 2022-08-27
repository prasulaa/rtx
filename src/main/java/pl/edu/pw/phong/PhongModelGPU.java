package pl.edu.pw.phong;

import com.aparapi.Kernel;
import pl.edu.pw.geometry.Point3D;
import pl.edu.pw.geometry.Sphere;
import pl.edu.pw.geometry.Vector3D;

public class PhongModelGPU extends Kernel {

    private final static float MAX_INTENSITY = 3.0f;

    private final float[][] result;

    private final float ka;
    private final float ia;
    private final float kd;
    private final float id;
    private final float ks;
    private final float is;
    private final float n;

    private final float sphereX;
    private final float sphereY;
    private final float sphereZ;
    private final float sphereR;

    private final float obsX;
    private final float obsY;
    private final float obsZ;

    private final float lightX;
    private final float lightY;
    private final float lightZ;

    public PhongModelGPU(Sphere sphere, Point3D observer,
                         PhongParameters parameters, Point3D light,
                         float[][] result) {
        super();

        ka = (float) parameters.getKa();
        ia = (float) parameters.getIa();
        kd = (float) parameters.getKd();
        id = (float) parameters.getId();
        ks = (float) parameters.getKs();
        is = (float) parameters.getIs();
        n = (float) parameters.getN();

        Point3D sphereCenter = sphere.getCenter();
        sphereX = (float) sphereCenter.getX();
        sphereY = (float) sphereCenter.getY();
        sphereZ = (float) sphereCenter.getZ();
        sphereR = (float) sphere.getRadius();

        obsX = (float) observer.getX();
        obsY = (float) observer.getY();
        obsZ = (float) observer.getZ();

        lightX = (float) light.getX();
        lightY = (float) light.getY();
        lightZ = (float) light.getZ();

        this.result = result;
    }

    @Override
    public void run() {
        int x = getGlobalId(0);
        int y = getGlobalId(1);


        //CALCULATING SPHERE POINT
        float sphereTmp = pow(sphereR, 2f) - pow(x - sphereX, 2f) - pow(y - sphereY, 2f);
        if (sphereTmp < 0) {
            result[y][x] = 0;
            return;
        }
        sphereTmp = sqrt(sphereTmp);
        float spherePointX = x;
        float spherePointY = y;
        float spherePointZ = min(sphereZ + sphereTmp, sphereZ - sphereTmp);


        //CALCULATING VECTOR L
        float Lx = lightX - spherePointX;
        float Ly = lightY - spherePointY;
        float Lz = lightZ - spherePointZ;
        float vectorLength = sqrt(Lx*Lx + Ly*Ly + Lz*Lz);
        Lx = Lx / vectorLength;
        Ly = Ly / vectorLength;
        Lz = Lz / vectorLength;


        //CALCULATING VECTOR N
        float Nx = spherePointX - sphereX;
        float Ny = spherePointY - sphereY;
        float Nz = spherePointZ - sphereZ;
        vectorLength = sqrt(Nx*Nx + Ny*Ny + Nz*Nz);
        Nx /= vectorLength;
        Ny /= vectorLength;
        Nz /= vectorLength;


        //CALCULATING VECTOR R
        float Dx = -1 * Lx;
        float Dy = -1 * Ly;
        float Dz = -1 * Lz;
        float DdotNTimes2 = 2 * dotProduct(Dx, Dy, Dz, Nx, Ny, Nz);
        float Rx = Dx - Nx * DdotNTimes2;
        float Ry = Dy - Ny * DdotNTimes2;
        float Rz = Dz - Nz * DdotNTimes2;
        vectorLength = sqrt(Rx*Rx + Ry*Ry + Rz*Rz);
        Rx /= vectorLength;
        Ry /= vectorLength;
        Rz /= vectorLength;


        //CALCULATING VECTOR V
        float Vx = obsX - spherePointX;
        float Vy = obsY - spherePointY;
        float Vz = obsZ - spherePointZ;
        vectorLength = sqrt(Vx*Vx + Vy*Vy + Vz*Vz);
        Vx /= vectorLength;
        Vy /= vectorLength;
        Vz /= vectorLength;


        // CALCULATING INTENSITY COMPONENTS
        float RdotV = dotProduct(Rx, Ry, Rz, Vx, Vy, Vz);
        if (RdotV < 0) {
            RdotV = 0;
        }

        float ambient = max(ka * ia, 0f);
        float diffuse = max(kd * id * dotProduct(Lx, Ly, Lz, Nx, Ny, Nz), 0f);
        float specular = max(ks * is * pow(RdotV, n), 0f);

        result[y][x] = (ambient + diffuse + specular) / MAX_INTENSITY;
    }

    private static float dotProduct(float x1, float y1, float z1,
                                    float x2, float y2, float z2) {
        return x1 * x2 + y1 * y2 + z1 * z2;
    }

}
