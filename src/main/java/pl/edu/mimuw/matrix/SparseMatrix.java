package pl.edu.mimuw.matrix;

import java.util.Arrays;

import static java.lang.Math.*;

// Ewentualnie można wywalić sparse i full do jakiejś abstrkcyjnej i będą się tylko różnić getem
public class SparseMatrix implements IDoubleMatrix {
    private final Shape shape;
    private final MatrixCellValue[] values;

    public SparseMatrix(Shape shape, MatrixCellValue[] values) {
        this.shape = shape;
        this.values = values;
    }

    public IDoubleMatrix times(IDoubleMatrix other) {
        assert this.shape().columns == other.shape().rows && this.shape().rows == other.shape().columns;
        double[][] result = new double[this.shape().rows][other.shape().columns];
        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < other.shape().columns; j++) {
                for (int k = 0; k < this.shape().columns; k++) {
                    result[i][j] += this.get(i, k) * other.get(k, j);
                }
            }
        }
        return new FullMatrix(result);
    }

    public IDoubleMatrix times(double scalar) {
        return new SparseMatrix(this.shape(),
                Arrays.stream(this.values)
                        .map(value -> new MatrixCellValue(value.row, value.column, value.value * scalar))
                        .toArray(MatrixCellValue[]::new));
    }

    public IDoubleMatrix plus(IDoubleMatrix other) {
        assert this.shape().equals(other.shape());
        double[][] result = new double[this.shape().rows][this.shape().columns];
        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < this.shape().columns; j++) {
                result[i][j] = this.get(i, j) + other.get(i, j);
            }
        }
        return new FullMatrix(result);
    }

    public IDoubleMatrix plus(double scalar) {
        double[][] result = new double[this.shape().rows][this.shape().columns];
        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < this.shape().columns; j++) {
                result[i][j] = this.get(i, j) + scalar;
            }
        }
        return new FullMatrix(result);
    }

    public IDoubleMatrix minus(IDoubleMatrix other) {
        assert this.shape().equals(other.shape());
        double[][] result = new double[this.shape().rows][this.shape().columns];
        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < this.shape().columns; j++) {
                result[i][j] = this.get(i, j) - other.get(i, j);
            }
        }
        return new FullMatrix(result);
    }

    public IDoubleMatrix minus(double scalar) {
        double[][] result = new double[this.shape().rows][this.shape().columns];
        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < this.shape().columns; j++) {
                result[i][j] = this.get(i, j) - scalar;
            }
        }
        return new FullMatrix(result);
    }

    public double get(int row, int column) {
        for (MatrixCellValue value : values) {
            if (value.row == row && value.column == column) {
                return value.value;
            }
        }
        return 0;
    }

    public double[][] data() {
        double[][] result = new double[this.shape().rows][this.shape().columns];
        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < this.shape().columns; j++) {
                result[i][j] = this.get(i, j);
            }
        }
        return result;
    }


    public double normOne() {
        double result = 0;
        for (int i = 0; i < this.shape().columns; i++) {
            double sum = 0;
            for (int j = 0; j < this.shape().rows; j++) {
                sum += abs(this.get(j, i));
            }
            result = max(result, sum);
        }
        return result;
    }
    //TODO: usprawnić obliczanie normy dla tego typu macierzy
    public double normInfinity() {
        double result = 0;
        for (int i = 0; i < this.shape().rows; i++) {
            double sum = 0;
            for (int j = 0; j < this.shape().columns; j++) {
                sum += abs(this.get(i, j));
            }
            result = max(result, sum);
        }
        return result;
    }

    public double frobeniusNorm() {
        double result = 0;
        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < this.shape().columns; j++) {
                result += this.get(i, j) * this.get(i, j);
            }
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
