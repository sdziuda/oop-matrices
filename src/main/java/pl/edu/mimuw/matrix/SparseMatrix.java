package pl.edu.mimuw.matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.*;

//Trzeba jakoś zoptymalizować dodawania/odejmowania/mnożenia sparsów
public class SparseMatrix implements IDoubleMatrix {
    private final Shape shape;
    private final List<MatrixCellValue>[] values;

    public SparseMatrix(Shape shape, MatrixCellValue... values) {
        assert shape.rows > 0 && shape.columns > 0;

        this.shape = shape;

        assert values != null;
        for (var value : values) {
            shape.assertInShape(value.row, value.column);
        }

        this.values = new ArrayList[shape.columns];
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        return new SparseMatrix(this.shape(),
                Arrays.stream(this.values)
                        .map(value -> new MatrixCellValue(value.row, value.column, value.value * scalar))
                        .toArray(MatrixCellValue[]::new));
    }

    @Override
    public IDoubleMatrix plus(IDoubleMatrix other) {
        assert this.shape().equals(other.shape());

        if (this.getClass() == other.getClass()) {
            MatrixCellValue[] newValues = new MatrixCellValue[this.values.length];
            int i = 0;

            for (int i = 0; i < this.values.length; i++) {

            }
        } else {

        }
    }

    public double get(int row, int column) {
        this.shape.assertInShape(row, column);

        for (MatrixCellValue value : this.values) {
            if (value.row == row && value.column == column) {
                return value.value;
            }
        }

        return 0;
    }

    public double[][] data() {
        final double[][] result = new double[this.shape().rows][this.shape().columns];

        for (var value : this.values) {
            result[value.row][value.column] = value.value;
        }

        return result;
    }

    public double normOne() {
        final double[] columnSums = new double[this.shape().columns];

        for (var value : this.values) {
            columnSums[value.column] += abs(value.value);
        }

        return Arrays.stream(columnSums).max().isPresent() ? Arrays.stream(columnSums).max().getAsDouble() : 0;
    }

    public double normInfinity() {
        final double[] rowSums = new double[this.shape().rows];

        for (var value : this.values) {
            rowSums[value.row] += abs(value.value);
        }

        return Arrays.stream(rowSums).max().isPresent() ? Arrays.stream(rowSums).max().getAsDouble() : 0;
    }

    public double frobeniusNorm() {
        double result = 0;

        for (var value : this.values) {
            result += value.value * value.value;
        }

        return sqrt(result);
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("TODO");
    }

    public Shape shape() {
        return this.shape;
    }
}
