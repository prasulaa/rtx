package pl.edu.pw;

import com.aparapi.Range;
import com.aparapi.Kernel;
import com.aparapi.device.Device;
import com.aparapi.device.OpenCLDevice;
import com.aparapi.internal.kernel.KernelManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.edu.pw.controller.Controller;

import java.io.IOException;


public class Main extends Application {

//    public static void main(String[] _args) {
//        final int w = 512;
//        final int h = 256;
//        final float[][] a = new float[h][w];
//        final float[][] b = new float[h][w];
//
//        /* fill the arrays with random values */
//        for (int i = 0; i < h; i++){
//            for (int j = 0; j < w; j++) {
//                a[i][j] = (float) (Math.random() * 100);
//                b[i][j] = (float) (Math.random() * 100);
//            }
//        }
//        final float[][] sum = new float[h][w];
//        final float[][] sumTmp = new float[h][w];
//
//        Kernel kernel = new Kernel(){
//            @Override public void run() {
//                int x = getGlobalId(0);
//                int y = getGlobalId(1);
//                for (int i = 0; i < 30000; i++) {
//                    sum[y][x] = i*a[y][x] + b[y][x];
//                }
//            }
//        };
//
//        long start = System.nanoTime();
//        kernel.execute(Range.create2D(w, h));
//        long stop = System.nanoTime();
//        System.out.println("time: " + (stop-start)/1e6);
//
//        start = System.nanoTime();
//        run(w, h,sumTmp, a, b);
//        stop = System.nanoTime();
//        System.out.println("time: " + (stop-start)/1e6);
//
//        System.out.printf("%6.2f + %6.2f = %8.2f\n", a[0][0], b[0][0], sum[0][0]);
//        System.out.printf("%6.2f + %6.2f = %8.2f\n", a[h-1][w-1], b[h-1][w-1], sum[h-1][w-1]);
//
//        for (int y = 0; y < h; y++) {
//            for (int x = 0; x < w; x++) {
//                if (sum[y][x] != sumTmp[y][x]) {
//                    throw new IllegalArgumentException("BLAD KURWA");
//                }
//            }
//        }
//
//
//        kernel.dispose();
//    }
//
//    public static void run(int w, int h, float[][] sum, float a[][], float b[][]) {
//        for (int y = 0; y < h; y++) {
//            for (int x = 0; x < w; x++) {
//                for (int i = 0; i < 30000; i++) {
//                    sum[y][x] = i * a[y][x] + b[y][x];
//                }
//            }
//        }
//    }

    public static void main(String[] args) {
        try {
            Controller.h = Integer.parseInt(args[0]);
            Controller.w = Integer.parseInt(args[1]);
        } catch (ArrayIndexOutOfBoundsException ignored) {
        } catch (NumberFormatException e) {
            System.err.println("Wrong args format");
        }

        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Pane mainPane = FXMLLoader.load(getClass().getResource("/mainPane.fxml"));
        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.setTitle("Ray tracer");
        stage.show();
        stage.setResizable(false);
    }

}
