package pl.edu.mimuw.matrix;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AntiDiagonalMatrix extends DiagonalIshMatrix {

    public AntiDiagonalMatrix(double... values) {
        super(values);
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        final double[] result = new double[this.values.length];

        for (int i = 0; i < this.values.length; i++) {
            result[i] = this.values[i] * scalar;
        }

        return new AntiDiagonalMatrix(result);
    }

    public double get(int row, int column) {
        this.shape().assertInShape(row, column);

        if (row + column == this.values.length - 1) {
            return this.values[column];
        } else {
            return 0;
        }
    }

    public double[][] data() {
        final double[][] result = new double[this.values.length][this.values.length];
        for (int i = 0; i < this.values.length; i++) {
            result[this.values.length - i - 1][i] = this.values[i];
        }
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final double[][] data = this.data();

        sb.append("Anti-diagonal matrix (").append(this.values.length).append("x").append(this.values.length)
                .append("):\n");
        for (int i = 0; i < this.values.length; i++) {
            boolean dots = false;
            for (int j = 0; j < this.values.length; j++) {
                if (j > 0 && j < this.values.length - 1 && data[i][j] == data[i][j - 1] && data[i][j] == data[i][j + 1]
                        && !dots) {

                    sb.append("... ");
                    dots = true;
                } else if (j == 0 || j == this.values.length - 1 || i == this.values.length - j + 1
                        || data[i][j] != data[i][j - 1] || data[i][j] != data[i][j + 1]) {

                    sb.append(BigDecimal.valueOf(data[i][j]).setScale(1, RoundingMode.HALF_UP)).append(" ");
                    dots = false;
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
