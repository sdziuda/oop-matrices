package pl.edu.mimuw.matrix;

import static java.lang.Math.*;

public class FullMatrix implements IDoubleMatrix {
    private final double[][] values;

    public FullMatrix(double[][] values) {
        assert values != null && values.length > 0 && values[0].length > 0;

        final int columns = values[0].length;
        for (var row : values) {
            assert row.length == columns;
        }

        this.values = values;
    }

    public double get(int row, int column) {
        this.shape().assertInShape(row, column);

        return this.values[row][column];
    }

    public double[][] data() {
        final double[][] result = new double[this.shape().rows][this.shape().columns];

        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < this.shape().columns; j++) {
                result[i][j] = this.values[i][j];
            }
        }

        return result;
    }

    public double normOne() {
        double result = 0;
        for (int i = 0; i < this.shape().columns; i++) {
            double sum = 0;
            for (int j = 0; j < this.shape().rows; j++) {
                sum += abs(this.values[j][i]);
            }
            result = max(result, sum);
        }
        return result;
    }

    public double normInfinity() {
        double result = 0;
        for (int i = 0; i < this.shape().rows; i++) {
            double sum = 0;
            for (int j = 0; j < this.shape().columns; j++) {
                sum += abs(this.values[i][j]);
            }
            result = max(result, sum);
        }
        return result;
    }

    public double frobeniusNorm() {
        double result = 0;
        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < this.shape().columns; j++) {
                result += this.values[i][j] * this.values[i][j];
            }
        }
        return sqrt(result);
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("TODO");
    }

    public Shape shape() {
        return Shape.matrix(this.values.length, this.values[0].length);
    }
}
