package pl.edu.mimuw.matrix;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class ConstMatrix implements IDoubleMatrix {
    protected final Shape shape;
    private final double value;

    public ConstMatrix(double value, Shape shape) {
        assert shape.rows > 0 && shape.columns > 0;

        this.value = value;
        this.shape = shape;
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        return new ConstMatrix(this.value * scalar, this.shape);
    }

    @Override
    public IDoubleMatrix plus(double scalar) {
        return new ConstMatrix(this.value + scalar, this.shape);
    }

    @Override
    public IDoubleMatrix minus(double scalar) {
        return new ConstMatrix(this.value - scalar, this.shape);
    }

    public double get(int row, int column) {
        this.shape.assertInShape(row, column);

        return this.value;
    }

    public double[][] data() {
        double[][] data = new double[this.shape.rows][this.shape.columns];

        for (int row = 0; row < this.shape.rows; row++) {
            for (int column = 0; column < this.shape.columns; column++) {
                data[row][column] = this.value;
            }
        }

        return data;
    }

    public double normOne() {
        return this.shape.rows * abs(this.value);
    }

    public double normInfinity() {
        return this.shape.columns * abs(this.value);
    }

    public double frobeniusNorm() {
        return sqrt(this.shape.rows * this.shape.columns) * abs(this.value);
    }

    @Override
    public String toString() {
        final var sb = new StringBuilder();

        sb.append("Const matrix (").append(this.shape.rows).append("x").append(this.shape.columns).append(")\n");
        for (int row = 0; row < this.shape.rows; row++) {
            if (this.shape.columns >= 3) {
                sb.append(BigDecimal.valueOf(this.value).setScale(1, RoundingMode.HALF_UP)).append(" ... ")
                        .append(BigDecimal.valueOf(this.value).setScale(1, RoundingMode.HALF_UP));
            } else {
                for (int column = 0; column < this.shape.columns; column++) {
                    sb.append(BigDecimal.valueOf(this.value).setScale(1, RoundingMode.HALF_UP));
                    if (column < this.shape.columns - 1) {
                        sb.append(" ");
                    }
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public Shape shape() {
        return this.shape;
    }
}
