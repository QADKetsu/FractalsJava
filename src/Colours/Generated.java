package Colours;
import java.awt.Color;
import java.util.*;

import org.apache.commons.math3.analysis.*;
import org.apache.commons.math3.analysis.interpolation.*;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm;

import MathHelper.MonotoneCubicSpline;

public class Generated {
    // uses cubic interpolation to generate a colour map

    public static HashMap<Integer, Color> firstMap() { // soft pastel rainbow
        // this shall be white blue black 
        HashMap<Integer, Color> map = new HashMap<Integer, Color>();
        // i will use HSV for the interpolation
        double h = 330/360.0;
        double[] hPrim = new double[] {(h-4.0) % 360, (h-2.0) % 360, (h-1.0) % 360, h, (h+1.0) % 360, (h+2.0) % 360, (h+4.0) % 360};
        Double[] sArray = new Double[] {0.0, 0.15, 0.33, 0.5, 0.65, 0.84, 1.0};
        double[] sPrim = new double[] {0.5, 1 - 0.5/2, 1 - 0.5/2, 1 - 0.5/3, 1 - 0.5/4, 1 - 0.5/5, 1.0};
        List<Double> sList = Arrays.asList(sArray);
        Double[] vArray = new Double[] {1.0, 0.86, 0.70, 0.56, 0.48, 0.30, 0.0};
        double[] vPrim = new double[] {1.0, 0.5 + 1/3, 0.5 + 0.5/3, 0.5, 0.5 - 0.5/3, 05 - 1/3, 0.0};
        List<Double> vList = Arrays.asList(vArray);

        Double[] xArray = new Double[] {(double) 0,(double) 1/6,(double) 2/6,(double) 3/6,(double) 4/6,(double) 5/6,(double) 6/6};
        double[] xPrim = new double[] {(double) 0,(double) 1/6,(double) 2/6,(double) 3/6,(double) 4/6,(double) 5/6,(double) 6/6};
        List<Double> xList = Arrays.asList(xArray); 

        MonotoneCubicSpline sSpline = MonotoneCubicSpline.createMonotoneCubicSpline(xList, sList);
        MonotoneCubicSpline vSpline = MonotoneCubicSpline.createMonotoneCubicSpline(xList, vList);

        UnivariateInterpolator hInterpolator = new SplineInterpolator();
        UnivariateInterpolator sInterpolator = new SplineInterpolator();
        UnivariateInterpolator vInterpolator = new SplineInterpolator();

        UnivariateFunction hFunction = hInterpolator.interpolate(xPrim, hPrim);
        UnivariateFunction sFunction = sInterpolator.interpolate(xPrim, sPrim);
        UnivariateFunction vFunction = vInterpolator.interpolate(xPrim, vPrim);

        for (int i = 0; i < 4096; i++) {
            // double s = sSpline.interpolate(i/255.0);
            // double v = vSpline.interpolate(i/255.0);
            double hModded = hFunction.value(i/4096.0);
            double s = sFunction.value(i/8192.0);
            double v = vFunction.value(i/8192.0);
            
            Color c = Color.getHSBColor((float) hModded, (float) s, (float) v);
            map.put(i, c);
        }
        return map;
    }

    public static HashMap<Integer, Color> secondMap() {
        // eef0f2,a2708a,6c756b,0f0f0f,3b1c32,1b1f3b,2a4494
        HashMap<Integer, Color> map = new HashMap<Integer, Color>();
        Color color1 = Color.decode("#D1D6DC");
        Color color2 = Color.decode("#B78FA3");
        Color color6 = Color.decode("#4D58A8");
        Color color7 = Color.decode("#AFBEE9");

        double[] control = new double[] {0.0/3, 1.0/3, 2.0/3, 3.0/3};
        double[] red = new double[] {color1.getRed(), color2.getRed(), color6.getRed(), color7.getRed()};
        double[] green = new double[] {color1.getGreen(), color2.getGreen(), color6.getGreen(), color7.getGreen()};
        double[] blue = new double[] {color1.getBlue(), color2.getBlue(), color6.getBlue(), color7.getBlue()};

        PolynomialFunctionLagrangeForm redFunction = new PolynomialFunctionLagrangeForm(control, red);
        PolynomialFunctionLagrangeForm greenFunction = new PolynomialFunctionLagrangeForm(control, green);
        PolynomialFunctionLagrangeForm blueFunction = new PolynomialFunctionLagrangeForm(control, blue);

        for (int i = 0; i < 4096; i++) {
            double redValue = redFunction.value(i/4096.0) % 256;
            double greenValue = greenFunction.value(i/4096.0) % 256;
            double blueValue = blueFunction.value(i/4096.0) % 256;

            if (redValue < 0) {
                redValue = 0;
            }
            if (greenValue < 0) {
                greenValue = 0;
            }
            if (blueValue < 0) {
                blueValue = 0;
            }
            Color c = new Color((int) redValue, (int) greenValue, (int) blueValue);
            map.put(i, c);
        }

        return map;

    }



    public static HashMap<Integer, Color> seventhMap() { // (236 1 .39) (214 .84 .80) (180  .7 1) (40 1 1) (120 1 0.01)
        
        HashMap<Integer, Color> colourMap = new HashMap<Integer, Color>();
        // i will use HSV for the interpolation
        double[] hArray = new double[]{236/360.0, 214/360.0, 180/360.0, 40/360.0, 120/360.0};
        double[] sArray = new double[] {1.0, 0.84, 0.7, 1.0, 1.0};
        double[] vArray = new double[] {.39, .80, 1.0, 1.0, 0.01};
        double[] xArray = new double[] {0.0, 0.16, 0.42, 0.6425, 0.8575};
        double[] iterations = new double[] {0.0, 1024/4.0, 1024/2.0, 3*1024/4.0, 1024.0};


        PolynomialFunctionLagrangeForm lagrangeForm = new PolynomialFunctionLagrangeForm(iterations, xArray);
        PolynomialFunctionLagrangeForm lagrangeForm1 = new PolynomialFunctionLagrangeForm(xArray, hArray);
        PolynomialFunctionLagrangeForm lagrangeForm2 = new PolynomialFunctionLagrangeForm(xArray, sArray);
        PolynomialFunctionLagrangeForm lagrangeForm3 = new PolynomialFunctionLagrangeForm(xArray, vArray);

        for (int i = 0; i < 1024; i++) {
            double point = lagrangeForm.value(i);
            double hVal = lagrangeForm1.value(point);
            double sVal = lagrangeForm2.value(point);
            double vVal = lagrangeForm3.value(point);
            colourMap.put(i, Color.getHSBColor((float) hVal, (float) sVal, (float) vVal));
        }


        return colourMap;
    }

    public static HashMap<Integer, Color> eighthMap() { // rgbw
        HashMap<Integer, Color> colourMap = new HashMap<>();
        // range of colour map is 0-1
        // at 0, white
        // at 0.25, red
        // at 0.5, green
        // at 0.75, blue
        // at 1, white

        double[] control = new double[] {0.0, 0.25, 0.5, 0.75, 1.0};
        double[] r = new double[] {255, 255, 0, 0, 255};
        double[] g = new double[] {255, 0, 255, 0, 255};
        double[] b = new double[] {255, 0, 0, 255, 255};

        PolynomialFunctionLagrangeForm lagrangeForm = new PolynomialFunctionLagrangeForm(control, r);
        PolynomialFunctionLagrangeForm lagrangeForm1 = new PolynomialFunctionLagrangeForm(control, g);
        PolynomialFunctionLagrangeForm lagrangeForm2 = new PolynomialFunctionLagrangeForm(control, b);

        for (int i = 0; i < 1024; i++) {
            double point = i/1023.0;
            double rVal = lagrangeForm.value(point) % 255;
            double gVal = lagrangeForm1.value(point) % 255;
            double bVal = lagrangeForm2.value(point) % 255;

            if (rVal < 0) {
                rVal = 0;
            }
            if (gVal < 0) {
                gVal = 0;
            }
            if (bVal < 0) {
                bVal = 0;
            }
            colourMap.put(i, new Color((int) rVal, (int) gVal, (int) bVal));
        }
        return colourMap;
    }

    public static HashMap<Integer, Color> ninthMap() { // coolours .co
        HashMap<Integer, Color> colourMap = new HashMap<>();
        // FF596F,E55F86,CB649C,B16AB3,966FCA,7C75E0,627AF7
        Color color1 = Color.decode("#FF596F");
        Color color2 = Color.decode("#E55F86");
        Color color3 = Color.decode("#CB649C");
        Color color4 = Color.decode("#B16AB3");
        Color color5 = Color.decode("#966FCA");
        Color color6 = Color.decode("#7C75E0");
        Color color7 = Color.decode("#627AF7");

        double[] control = new double[] {0.0/6, 1.0/6, 2.0/6, 3.0/6, 4.0/6, 5.0/6, 6.0/6};
        double[] r = new double[] {color1.getRed(), color2.getRed(), color3.getRed(), color4.getRed(), color5.getRed(), color6.getRed(), color7.getRed()};
        double[] g = new double[] {color1.getGreen(), color2.getGreen(), color3.getGreen(), color4.getGreen(), color5.getGreen(), color6.getGreen(), color7.getGreen()};
        double[] b = new double[] {color1.getBlue(), color2.getBlue(), color3.getBlue(), color4.getBlue(), color5.getBlue(), color6.getBlue(), color7.getBlue()};

        PolynomialFunctionLagrangeForm lagrangeForm = new PolynomialFunctionLagrangeForm(control, r);
        PolynomialFunctionLagrangeForm lagrangeForm1 = new PolynomialFunctionLagrangeForm(control, g);
        PolynomialFunctionLagrangeForm lagrangeForm2 = new PolynomialFunctionLagrangeForm(control, b);

        for (int i = 0; i < 1024; i++) {
            double point = i/1023.0;
            double rVal = lagrangeForm.value(point) % 255;
            double gVal = lagrangeForm1.value(point) % 255;
            double bVal = lagrangeForm2.value(point) % 255;

            if (rVal < 0) {
                rVal = 0;
            }
            if (gVal < 0) {
                gVal = 0;
            }
            if (bVal < 0) {
                bVal = 0;
            }
            colourMap.put(i, new Color((int) rVal, (int) gVal, (int) bVal));
        }
        return colourMap;
    }
}
        