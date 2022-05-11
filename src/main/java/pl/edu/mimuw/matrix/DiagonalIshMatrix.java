package pl.edu.mimuw.matrix;

import static java.lang.Math.*;

public abstract class DiagonalIshMatrix implements IDoubleMatrix{
    protected double[] values;

    protected DiagonalIshMatrix(double[] values){
        this.values = values;
    }

    public IDoubleMatrix times(double scalar) {
        double[] result = new double[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i] * scalar;
        }
        return new DiagonalMatrix(result);
    }

    public double normOne() {
        double result = 0;
        for (double value : values) {
            result = max(result, abs(value));
        }
        return result;
    }

    public double normInfinity() {
        double result = 0;
        for (double value : values) {
            result = max(result, abs(value));
        }
        return result;
    }

    public double frobeniusNorm() {
        double result = 0;
        for (double value : values) {
            result += value * value;
        }
        return sqrt(result);
    }

    public Shape shape() {
        return Shape.matrix(values.length, values.length);
    }
}
