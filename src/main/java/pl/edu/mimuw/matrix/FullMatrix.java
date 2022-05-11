package pl.edu.mimuw.matrix;

import static java.lang.Math.*;

public class FullMatrix implements IDoubleMatrix {
    private final double[][] values;

    public FullMatrix(double[][] values) {
        this.values = values;
    }

    public IDoubleMatrix times(IDoubleMatrix other) {
        assert this.shape().columns == other.shape().rows && this.shape().rows == other.shape().columns;
        double[][] result = new double[this.shape().rows][other.shape().columns];
        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < other.shape().columns; j++) {
                for (int k = 0; k < this.shape().columns; k++) {
                    result[i][j] += this.values[i][k] * other.get(k, j);
                }
            }
        }
        return new FullMatrix(result);
    }

    public IDoubleMatrix times(double scalar) {
        double[][] result = new double[this.shape().rows][this.shape().columns];
        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < this.shape().columns; j++) {
                result[i][j] = this.values[i][j] * scalar;
            }
        }
        return new FullMatrix(result);
    }

    public IDoubleMatrix plus(IDoubleMatrix other) {
        assert this.shape().equals(other.shape());
        double[][] result = new double[this.shape().rows][this.shape().columns];
        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < this.shape().columns; j++) {
                result[i][j] = this.values[i][j] + other.get(i, j);
            }
        }
        return new FullMatrix(result);
    }

    public IDoubleMatrix plus(double scalar) {
        double[][] result = new double[this.shape().rows][this.shape().columns];
        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < this.shape().columns; j++) {
                result[i][j] = this.values[i][j] + scalar;
            }
        }
        return new FullMatrix(result);
    }

    public IDoubleMatrix minus(IDoubleMatrix other) {
        assert this.shape().equals(other.shape());
        double[][] result = new double[this.shape().rows][this.shape().columns];
        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < this.shape().columns; j++) {
                result[i][j] = this.values[i][j] - other.get(i, j);
            }
        }
        return new FullMatrix(result);
    }

    public IDoubleMatrix minus(double scalar) {
        double[][] result = new double[this.shape().rows][this.shape().columns];
        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < this.shape().columns; j++) {
                result[i][j] = this.values[i][j] - scalar;
            }
        }
        return new FullMatrix(result);
    }

    public double get(int row, int column) {
        return this.values[row][column];
    }

    public double[][] data() {
        double[][] result = new double[this.shape().rows][this.shape().columns];
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
