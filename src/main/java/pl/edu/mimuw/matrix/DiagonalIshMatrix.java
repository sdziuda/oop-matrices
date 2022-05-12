package pl.edu.mimuw.matrix;

import static java.lang.Math.*;

public abstract class DiagonalIshMatrix implements IDoubleMatrix{
    protected final double[] values;

    protected DiagonalIshMatrix(double[] values){
        assert values != null && values.length > 0;

        this.values = values;
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

    public Shape shape() {
        return Shape.matrix(this.values.length, this.values.length);
    }
}
