import GUI.FractalFrame;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Fractals");
        FractalFrame frame = new FractalFrame(1000,1000);
        frame.run();
    }
}
