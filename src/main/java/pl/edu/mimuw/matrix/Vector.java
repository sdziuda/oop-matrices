package pl.edu.mimuw.matrix;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import static java.lang.Math.*;

public class Vector implements IDoubleMatrix {
    private final double[] values;

    public Vector(double... values) {
        assert values != null && values.length > 0;

        this.values = new double[values.length];
        System.arraycopy(values, 0, this.values, 0, values.length);
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        final double[] result = new double[this.values.length];

        for (int i = 0; i < this.values.length; i++) {
            result[i] = this.values[i] * scalar;
        }

        return new Vector(result);
    }

    @Override
    public IDoubleMatrix plus(IDoubleMatrix other) {
        assert this.shape().equals(other.shape());

        final double[] result = new double[this.values.length];

        for (int i = 0; i < this.values.length; i++) {
            result[i] = this.values[i] + other.get(i, 0);
        }

        return new Vector(result);
    }

    @Override
    public IDoubleMatrix plus(double scalar) {
        final double[] result = new double[this.values.length];

        for (int i = 0; i < this.values.length; i++) {
            result[i] = this.values[i] + scalar;
        }

        return new Vector(result);
    }

    @Override
    public IDoubleMatrix minus(IDoubleMatrix other) {
        assert this.shape().equals(other.shape());

        final double[] result = new double[this.values.length];

        for (int i = 0; i < this.values.length; i++) {
            result[i] = this.values[i] - other.get(i, 0);
        }

        return new Vector(result);
    }

    @Override
    public IDoubleMatrix minus(double scalar) {
        final double[] result = new double[this.values.length];

        for (int i = 0; i < this.values.length; i++) {
            result[i] = this.values[i] - scalar;
        }

        return new Vector(result);
    }

    public double get(int row, int column) {
        this.shape().assertInShape(row, column);

        return this.values[row];
    }

    public double[][] data() {
        final double[][] result = new double[this.values.length][1];

        for (int i = 0; i < this.values.length; i++) {
            result[i][0] = this.values[i];
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
        final var sb = new StringBuilder();

        sb.append("Vector (").append(this.values.length).append("x1):\n");
        Arrays.stream(this.values)
                .forEach(value -> sb.append(BigDecimal.valueOf(value).setScale(1, RoundingMode.HALF_UP))
                        .append("\n"));

        return sb.toString();
    }

    public Shape shape() {
        return Shape.vector(this.values.length);
    }
}