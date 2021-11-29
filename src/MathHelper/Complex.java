package MathHelper;
/**
 * Complex number class.
 * Includes basic operations.
 * @params real, imaginary
 * @returns Complex
 * @author kiriketsuki
 */

public class Complex {
    private double real;
    private double imaginary;

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    // getters
    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    // magnitude
    public double magnitude() {
        return Math.sqrt(real * real + imaginary * imaginary);
    }

    // conjugate
    public Complex conjugate() {
        return new Complex(real, -imaginary);
    }

    // add
    public Complex add(Complex c) {
        return new Complex(real + c.real, imaginary + c.imaginary);
    }

    // subtract
    public Complex subtract(Complex c) {
        return new Complex(real - c.real, imaginary - c.imaginary);
    }

    // multiply
    public Complex multiply(Complex c) {
        return new Complex(real * c.real - imaginary * c.imaginary, real * c.imaginary + imaginary * c.real);
    }

    // divide
    public Complex divide(Complex c) {
        return new Complex((real * c.real + imaginary * c.imaginary) / (c.real * c.real + c.imaginary * c.imaginary),
                (imaginary * c.real - real * c.imaginary) / (c.real * c.real + c.imaginary * c.imaginary));
    }

    // power
    public Complex power(int n) {
        if (n == 0) {
            return new Complex(1, 0);
        }
        if (n == 1) {
            return this;
        }
        if (n % 2 == 0) {
            Complex c = this.power(n / 2);
            return c.multiply(c);
        } else {
            Complex c = this.power(n / 2);
            return c.multiply(c).multiply(this);
        }
    }

    // toString
    public String toString() {
        if (imaginary == 0) {
            return String.format("%.2f", real);
        }
        if (real == 0) {
            return String.format("%.2fi", imaginary);
        }
        if (imaginary > 0) {
            return String.format("%.2f + %.2fi", real, imaginary);
        } else {
            return String.format("%.2f - %.2fi", real, -imaginary);
        }
    }
}
