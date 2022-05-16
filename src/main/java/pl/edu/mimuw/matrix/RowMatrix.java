package pl.edu.mimuw.matrix;

import java.util.Arrays;

import static java.lang.Math.sqrt;

public class RowMatrix extends RegularMatrix {

    public RowMatrix(Shape shape, double[] values) {
        super(shape, values);

        assert values.length == this.shape.columns;
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        return new RowMatrix(shape, Arrays.stream(this.values).map(x -> x * scalar).toArray());
    }

    @Override
    public IDoubleMatrix plus(double scalar) {
        return new RowMatrix(shape, Arrays.stream(this.values).map(x -> x + scalar).toArray());
    }

    @Override
    public IDoubleMatrix minus(double scalar) {
        return new RowMatrix(shape, Arrays.stream(this.values).map(x -> x - scalar).toArray());
    }

    public double get(int row, int column) {
        this.shape.assertInShape(row, column);

        return this.values[column];
    }

    public double[][] data() {
        final double[][] result = new double[this.shape.rows][this.shape.columns];

        for (int i = 0; i < this.shape.rows; i++) {
            for (int j = 0; j < this.shape.columns; j++) {
                result[i][j] = this.values[j];
            }
        }

        return result;
    }

    public double normOne() {
        return Arrays.stream(this.values).map(Math::abs).max().isPresent()
                ? Arrays.stream(this.values).map(Math::abs).max().getAsDouble() * this.shape.rows : 0;
    }

    public double normInfinity() {
        return Arrays.stream(this.values).map(Math::abs).sum();
    }

    public double frobeniusNorm() {
        return sqrt(Arrays.stream(this.values).map(Math::abs).map(x -> x * x * this.shape.rows).sum());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Row matrix (").append(this.shape.rows).append("x").append(this.shape.columns).append("):\n");
        for (int i = 0; i < this.shape.rows; i++) {
            for (int j = 0; j < this.shape.columns; j++) {
                sb.append(this.values[j]).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
