package MathHelper;

public class LinearInterpolation {
    public static double interpolate(double one, double two, double t) {
        return one * (1 - t) + two * t;
    }
}
