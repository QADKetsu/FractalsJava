package Colours;
import java.awt.Color;
import java.util.*;
import MathHelper.MonotoneCubicSpline;

public class Generated {
    // uses cubic interpolation to generate a colour map

    public static Color[] firstMap() {
        // this shall be white blue black 
        Color[] colourMap = new Color[256];
        // i will use HSV for the interpolation
        double h = 191.0/360.0;
        Double[] sArray = new Double[] {0.0, 0.15, 0.33, 0.5, 0.65, 0.84, 1.0};
        List<Double> sList = Arrays.asList(sArray);
        Double[] vArray = new Double[] {1.0, 0.86, 0.70, 0.56, 0.48, 0.30, 0.0};
        List<Double> vList = Arrays.asList(vArray);
        // interpolate s first
        Double[] xArray = new Double[] {(double) 0,(double) 1/6,(double) 2/6,(double) 3/6,(double) 4/6,(double) 5/6,(double) 6/6};
        List<Double> xList = Arrays.asList(xArray); 

        MonotoneCubicSpline sSpline = MonotoneCubicSpline.createMonotoneCubicSpline(xList, sList);
        MonotoneCubicSpline vSpline = MonotoneCubicSpline.createMonotoneCubicSpline(xList, vList);

        for (int i = 0; i < 256; i++) {
            double s = sSpline.interpolate(i/255.0);
            double v = vSpline.interpolate(i/255.0);
            
            colourMap[i] = Color.getHSBColor((float) h, (float) s, (float) v);
        }
        return colourMap;
    }
}
