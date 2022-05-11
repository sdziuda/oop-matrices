package pl.edu.mimuw.matrix;

import static java.lang.Math.*;

public class Vector implements IDoubleMatrix {
    private final double[] values;

    public Vector(double... values) {
        this.values = values;
    }

    public IDoubleMatrix times(IDoubleMatrix other) {
        assert other.shape().equals(this.shape());
        double[] result = new double[this.shape().rows];
        for (int i = 0; i < this.shape().rows; i++) {
            result[i] = this.get(i, 0) * other.get(i, 0);
        }
        return new Vector(result);
    }

    public IDoubleMatrix times(double scalar) {
        double[] result = new double[this.shape().rows];
        for (int i = 0; i < this.shape().rows; i++) {
            result[i] = this.get(0, i) * scalar;
        }
        return new Vector(result);
    }

    public IDoubleMatrix plus(IDoubleMatrix other) {
        assert other.shape().equals(this.shape());
        double[] result = new double[this.shape().rows];
        for (int i = 0; i < this.shape().rows; i++) {
            result[i] = this.get(i, 0) + other.get(i, 0);
        }
        return new Vector(result);
    }

    public IDoubleMatrix plus(double scalar) {
        double[] result = new double[this.shape().rows];
        for (int i = 0; i < this.shape().rows; i++) {
            result[i] = this.get(0, i) + scalar;
        }
        return new Vector(result);
    }

    public IDoubleMatrix minus(IDoubleMatrix other) {
        assert other.shape().equals(this.shape());
        double[] result = new double[this.shape().rows];
        for (int i = 0; i < this.shape().rows; i++) {
            result[i] = this.get(i, 0) - other.get(i, 0);
        }
        return new Vector(result);
    }

    public IDoubleMatrix minus(double scalar) {
        double[] result = new double[this.shape().rows];
        for (int i = 0; i < this.shape().rows; i++) {
            result[i] = this.get(0, i) - scalar;
        }
        return new Vector(result);
    }

    public double get(int row, int column) {
        assert row > 0 && row < this.values.length && column == 0;
        return this.values[row];
    }

    public double[][] data() {
        double[][] result = new double[this.values.length][1];
        for (int i = 0; i < this.values.length; i++) {
            result[i][0] = this.get(i, 0);
        }
        return result;
    }

    public double normOne() {
        double result = 0;
        for (double value : this.values) {
            result += abs(value);
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

    @Override
    public String toString() {
        throw new UnsupportedOperationException("TODO");
    }

    public Shape shape() {
        return Shape.vector(this.values.length);
    }
}