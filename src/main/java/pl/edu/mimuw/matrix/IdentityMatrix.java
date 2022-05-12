package pl.edu.mimuw.matrix;

import static java.lang.Math.sqrt;

public class IdentityMatrix implements IDoubleMatrix {
    private final int size;

    public IdentityMatrix(int size) {
        assert size > 0;

        this.size = size;
    }

    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
        assert this.shape().columns == other.shape().rows;

        final double[][] values = new double[other.shape().rows][other.shape().columns];

        for (int i = 0; i < other.shape().rows; i++) {
            for (int j = 0; j < other.shape().columns; j++) {
                values[i][j] = other.get(i, j);
            }
        }

        return new FullMatrix(values);
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        final double[] values = new double[this.size];

        for (int i = 0; i < this.size; i++) {
            values[i] = scalar;
        }

        return new DiagonalMatrix(values);
    }

    public double get(int row, int column) {
        this.shape().assertInShape(row, column);

        if (row == column) {
            return 1;
        } else {
            return 0;
        }
    }

    public double[][] data() {
        double[][] values = new double[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
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
        return sqrt(this.size);
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("TODO");
    }

    public Shape shape() {
        return Shape.matrix(size, size);
    }
}
