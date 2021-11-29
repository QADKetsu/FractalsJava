import GUI.FractalFrame;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Simple Mandelbrot Test");
        FractalFrame frame = new FractalFrame(2000,2000);
        frame.run();
    }
}
