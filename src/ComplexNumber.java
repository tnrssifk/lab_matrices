public class ComplexNumber {
    private double re;
    private double im;

    public ComplexNumber(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double getRe() { return re; }
    public double getIm() { return im; }

    public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(this.re + other.re, this.im + other.im);
    }

    public ComplexNumber sub(ComplexNumber other) {
        return new ComplexNumber(this.re - other.re, this.im - other.im);
    }

    public ComplexNumber mul(ComplexNumber other) {
        double newRe = this.re * other.re - this.im * other.im;
        double newIm = this.re * other.im + this.im * other.re;
        return new ComplexNumber(newRe, newIm);
    }

    public ComplexNumber div(ComplexNumber other) {
        double denom = other.re * other.re + other.im * other.im;
        if (denom == 0) {
            throw new ArithmeticException("деление на ноль!");
        }
        double newRe = (this.re * other.re + this.im * other.im) / denom;
        double newIm = (this.im * other.re - this.re * other.im) / denom;
        return new ComplexNumber(newRe, newIm);
    }

    @Override
    public String toString() {
        if (im >= 0) {
            return String.format("%.2f + %.2fi", re, im);
        } else {
            return String.format("%.2f - %.2fi", re, Math.abs(im));
        }
    }
}