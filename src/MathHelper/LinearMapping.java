package MathHelper;

public class LinearMapping {
    public static double map(double toMap, double a, double b, double c, double d) { // maps toMap from {a to b}, to {c to d}
        // * got from http://learnwebgl.brown37.net/08_projections/projections_mapping.html
        double scale  = (d-c)/(b-a);
        double offset = -a*scale + c;
        return toMap*scale + offset;
    }
}
