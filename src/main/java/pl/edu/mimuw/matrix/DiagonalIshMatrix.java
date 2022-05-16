package pl.edu.mimuw.matrix;

import static java.lang.Math.*;

public abstract class DiagonalIshMatrix extends RegularMatrix {

    protected DiagonalIshMatrix(double... values) {
        super(Shape.matrix(values.length, values.length), values);
    }

    public double normOne() {
        double result = 0;
        for (double value : this.values) {
            result = max(result, abs(value));
        }
        return result;
    }

    public double normInfinity() {
        double result = 0;
        for (double value : this.values) {
            result = max(result, abs(value));
        }
        return result;
    }

    public double frobeniusNorm() {
        double result = 0;
        for (double value : this.values) {
            result += value * value;
        }
        return sqrt(result);
    }
}
