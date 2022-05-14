package pl.edu.mimuw.matrix;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

        return other;
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
        final StringBuilder sb = new StringBuilder();
        final double[][] data = this.data();

        sb.append("Identity matrix (").append(this.size).append("x").append(this.size).append("):\n");
        for (int i = 0; i < this.size; i++) {
            boolean dots = false;
            for (int j = 0; j < this.size; j++) {
                if (j > 0 && j < this.size - 1 && data[i][j] == data[i][j - 1] && data[i][j] == data[i][j + 1]
                        && !dots) {

                    sb.append("... ");
                    dots = true;
                } else if (j == 0 || j == this.size - 1 || i == j || data[i][j] != data[i][j - 1]
                        || data[i][j] != data[i][j + 1]) {

                    sb.append(BigDecimal.valueOf(data[i][j]).setScale(1, RoundingMode.HALF_UP)).append(" ");
                    dots = false;
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public Shape shape() {
        return Shape.matrix(this.size, this.size);
    }
}
