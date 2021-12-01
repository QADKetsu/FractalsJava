package Colours;
import java.awt.Color;
import java.util.*;

import org.apache.commons.math3.analysis.*;
import org.apache.commons.math3.analysis.interpolation.*;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm;

import MathHelper.LinearMapping;
import MathHelper.MonotoneCubicSpline;

public class Generated {
    // uses cubic interpolation to generate a colour map

    public static HashMap<Integer, Color> firstMap() {
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

        for (int i = 0; i < 256; i++) {
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

    public static HashMap<Integer, Color> secondMap() { // (236 1 .39) (214 .84 .80) (180  .7 1) (40 1 1) (120 1 0.01)
        
        HashMap<Integer, Color> colourMap = new HashMap<Integer, Color>();
        // i will use HSV for the interpolation
        Double[] hArray = new Double[]{236/360.0, 214/360.0, 180/360.0, 40/360.0, 120/360.0};
        List<Double> hList = Arrays.asList(hArray);
        Double[] sArray = new Double[] {1.0, 0.84, 0.7, 1.0, 1.0};
        List<Double> sList = Arrays.asList(sArray);
        Double[] vArray = new Double[] {.39, .80, 1.0, 1.0, 0.01};
        List<Double> vList = Arrays.asList(vArray);
        Double[] xArray = new Double[] {0.0, 0.16, 0.42, 0.6425, 0.8575};
        List<Double> xList = Arrays.asList(xArray);

        MonotoneCubicSpline hSpline = MonotoneCubicSpline.createMonotoneCubicSpline(xList, hList);
        MonotoneCubicSpline sSpline = MonotoneCubicSpline.createMonotoneCubicSpline(xList, sList);
        MonotoneCubicSpline vSpline = MonotoneCubicSpline.createMonotoneCubicSpline(xList, vList);

        List<Double> xList2 = Arrays.asList(new Double[] {0.0, 1.0});
        List<Double> mapSizeList = Arrays.asList(new Double[] {0.0, 256.0});
        MonotoneCubicSpline inverseSpline = MonotoneCubicSpline.createMonotoneCubicSpline(mapSizeList, xList2);

        for (int i = 0; i < 256; i++) {
            double inverse = inverseSpline.interpolate((double) i);
            double h = hSpline.interpolate(inverse);
            double s = sSpline.interpolate(inverse);
            double v = vSpline.interpolate(inverse);
            colourMap.put(i,Color.getHSBColor((float) h, (float) s, (float) v));
        }

        return colourMap;
    }

    public static HashMap<Integer,Color> thirdMap() {
        // doesnt fully work yet
        HashMap<Integer, Color> colourMap = new HashMap<Integer, Color>();
        // r: 66 25 9 4 0 12 44 24 57 134 211 241 248 255 204 153 106
        // g: 30 7 1 4 7 44 82 125 181 236 233 201 170 128 87 52
        // b: 15 26 47 73 100 138 177 209 229 248 191 95 0 0 0 3
        double[] rArray = new double[] {255.0, 66.0, 25.0, 9.0, 4.0, 0.0, 12.0, 24.0, 57.0, 134.0, 211.0, 241.0, 248.0, 255.0, 204.0, 153.0, 106.0, 0.0};
        // List<Double> rList = Arrays.asList(rArray);
        double[] gArray = new double[] {255.0, 30.0, 7.0, 1.0, 4.0, 7.0, 44.0, 82.0, 125.0, 181.0, 236.0, 233.0, 201.0, 170.0, 128.0, 87.0, 52.0, 0.0};
        // List<Double> gList = Arrays.asList(gArray);
        double[] bArray = new double[] {255.0, 15.0, 26.0, 47.0, 73.0, 100.0, 138.0, 177.0, 209.0, 229.0, 248.0, 191.0, 95.0, 0.0, 0.0, 0.0, 3.0, 0.0};
        // List<Double> bList = Arrays.asList(bArray);
        double[] xArray = new double[] {0.0, 1.0/17, 2.0/17, 3.0/17, 4.0/17, 5.0/17, 6.0/17, 7.0/17, 8.0/17, 9.0/17, 10.0/17, 11.0/17, 12.0/17, 13.0/17, 14.0/17, 15.0/17, 16.0/17, 17.0/17};
        // List<Double> xList = Arrays.asList(xArray);

        // MonotoneCubicSpline rSpline = MonotoneCubicSpline.createMonotoneCubicSpline(xList, rList);
        // MonotoneCubicSpline gSpline = MonotoneCubicSpline.createMonotoneCubicSpline(xList, gList);
        // MonotoneCubicSpline bSpline = MonotoneCubicSpline.createMonotoneCubicSpline(xList, bList);

        // // create a spline for 0 - 1, and 255 - 0
        // List<Double> xList2 = Arrays.asList(new Double[] {0.0, 1.0});
        // List<Double> mapSList = Arrays.asList(new Double[] {0.0, 255.0});
        // MonotoneCubicSpline mapSizeSpline = MonotoneCubicSpline.createMonotoneCubicSpline(mapSList, xList2);

        // for (int i = 0; i < 256; i++) {
        //     double iD = i * mapSizeSpline.interpolate((double) i);
        //     double r = rSpline.interpolate(iD);
        //     double g = gSpline.interpolate(iD);
        //     double b = bSpline.interpolate(iD);
        //     colourMap.put(i, new Color((int) r % 256, (int) g % 256, (int) b % 256)) ;
        // }

        // lagrange interpolation
        PolynomialFunctionLagrangeForm lagrangeForm = new PolynomialFunctionLagrangeForm(xArray, rArray);
        PolynomialFunctionLagrangeForm lagrangeForm2 = new PolynomialFunctionLagrangeForm(xArray, gArray);
        PolynomialFunctionLagrangeForm lagrangeForm3 = new PolynomialFunctionLagrangeForm(xArray, bArray);

        for (int i = 0; i < 256; i++) {
            double iD = i;
            double r = lagrangeForm.value(iD);
            double g = lagrangeForm2.value(iD);
            double b = lagrangeForm3.value(iD);
            colourMap.put(i, new Color((int) r % 256, (int) g % 256, (int) b % 256)) ;
        }

        return colourMap;
    }

    public static HashMap<Integer, Color> fourthMap() {
        HashMap<Integer, Color> colourMap = new HashMap<>();
        // 5 points
        // 0 0.25 0.5 0.75 1
        // hsv (0 255 0)  (213 255 117) (180 255 246) (360 255 127) (360 255 0)
        double[] iterations = new double[] {0.0, 256/4.0, 256/2.0, 3*256/4.0, 256.0};
        double[] points = new double[] {0.0, 0.25, 0.5, 0.75, 1.0};
        double[] h = new double[] {0.0, 213.0, 180.0, 360.0, 360.0};
        double[] s = new double[] {255.0, 255.0, 255.0, 255.0, 0.0};
        double[] v = new double[] {0.0, 117.0, 246.0, 127.0, 0.0};

        UnivariateInterpolator iterInterpolator = new SplineInterpolator();
        UnivariateInterpolator hInterpolator = new SplineInterpolator();
        UnivariateInterpolator sInterpolator = new SplineInterpolator();
        UnivariateInterpolator vInterpolator = new SplineInterpolator();

        UnivariateFunction iterFunction = iterInterpolator.interpolate(iterations, points);
        UnivariateFunction hFunction = hInterpolator.interpolate(points, h);
        UnivariateFunction sFunction = sInterpolator.interpolate(points, s);
        UnivariateFunction vFunction = vInterpolator.interpolate(points, v);

        for (int i = 0; i < 256; i++) {
            double point = iterFunction.value(i);
            double hVal = hFunction.value(point);
            double sVal = sFunction.value(point);
            double vVal = vFunction.value(point);
            colourMap.put(i, Color.getHSBColor((float) hVal, (float) sVal, (float) vVal));
        }
        return colourMap;
    }

    public static HashMap<Integer, Color> fifthMap() {
        HashMap<Integer, Color> colourMap = new HashMap<>();
        // i want a circle
        // h constant at 217/360
        // v constant at 255
        // s varies from 0 to 255

        for (int i = 0; i < 256; i++) {
            double h = 217.0/360.0;
            double s = i/255.0;
            double v = 255.0/255;
            colourMap.put(i, Color.getHSBColor((float) h, (float) s, (float) v));
        }
        
        return colourMap;
    }

    public static HashMap<Integer, Color> sixthMap() {
        HashMap<Integer, Color> colourMap = new HashMap<>();
        // 8 control points
        // hsv: 104 0 100, 192 25 100, 214 53 100, 258 73 100, 319 100 50, 360 77 76, 0 39 94, 185 22 100
        double[] iterations = new double[] {0.0, 256/8.0, 2*256/8.0, 3*256/8.0, 4*256/8.0, 5*256/8.0, 6*256/8.0, 7*256/8.0, 256.0};
        double[] h = new double[] {185.0, 104.0, 192.0, 214.0, 258.0, 319.0, 360.0, 0.0, 185.0};
        double[] s = new double[] {22.0, 0.0, 25.0, 53.0, 73.0, 100.0, 77.0, 39.0, 22.0};
        double[] v = new double[] {100.0,100.0, 100.0, 100.0, 100.0, 50.0, 76.0, 94.0, 100.0};

        // lagrange interpolation
        PolynomialFunctionLagrangeForm lagrangeForm = new PolynomialFunctionLagrangeForm(iterations, h);
        PolynomialFunctionLagrangeForm lagrangeForm2 = new PolynomialFunctionLagrangeForm(iterations, s);
        PolynomialFunctionLagrangeForm lagrangeForm3 = new PolynomialFunctionLagrangeForm(iterations, v);

        for (int i = 0; i < 256; i++) {
            double hVal = lagrangeForm.value(i)/360;
            double sVal = lagrangeForm2.value(i)/100;
            double vVal = lagrangeForm3.value(i)/100;
            colourMap.put(i, Color.getHSBColor((float) hVal, (float) sVal, (float) vVal));
        }


        return colourMap;
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
}