package pl.edu.mimuw.matrix;

import static java.lang.Math.sqrt;

public class IdentityMatrix implements IDoubleMatrix {
    private final int size;

    public IdentityMatrix(int size) {
        this.size = size;
    }

    public IDoubleMatrix times(IDoubleMatrix other) {
        assert other.shape().columns == this.shape().rows && other.shape().rows == this.shape().columns;
        return other;
    }

    public IDoubleMatrix times(double scalar) {
        double[] values = new double[size];
        for (int i = 0; i < size; i++) {
            values[i] = scalar;
        }
        return new DiagonalMatrix(values);
    }

    public IDoubleMatrix plus(IDoubleMatrix other) {
        assert other.shape().equals(this.shape());
        throw new UnsupportedOperationException("TODO");
    }

    public IDoubleMatrix plus(double scalar) {
        double[][] values = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                values[i][j] = scalar;
                if (i == j) {
                    values[i][j]++;
                }
            }
        }
        return new FullMatrix(values);
    }

    public IDoubleMatrix minus(IDoubleMatrix other) {
        assert other.shape().equals(this.shape());
        throw new UnsupportedOperationException("TODO");
    }

    public IDoubleMatrix minus(double scalar) {
        return this.plus(-scalar);
    }

    public double get(int row, int column) {
        if (row == column) {
            return 1;
        } else {
            return 0;
        }
    }

    public double[][] data() {
        double[][] values = new double[size][size];
        for (int i = 0; i < size; i++) {
            values[i][i] = 1;
        }
        return values;
    }

    public double normOne() {
        return 1;
    }

    public double normInfinity() {
        return 1;
    }

    public double frobeniusNorm() {
        return sqrt(size);
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("TODO");
    }

    public Shape shape() {
        return Shape.matrix(size, size);
    }
}
