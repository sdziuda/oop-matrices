package pl.edu.mimuw.matrix;

public interface IDoubleMatrix {

    default IDoubleMatrix times(IDoubleMatrix other) {
        assert this.shape().columns == other.shape().rows;

        final double[][] result = new double[this.shape().rows][other.shape().columns];
        boolean isZero = true;

        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < other.shape().columns; j++) {
                for (int k = 0; k < this.shape().columns; k++) {
                    result[i][j] += this.get(i, k) * other.get(k, j);
                    if (result[i][j] != 0) isZero = false;
                }
            }
        }

        if (isZero) return new ZeroMatrix(this.shape());
        return new FullMatrix(result);
    }

    default IDoubleMatrix times(double scalar) {
        final double[][] result = new double[this.shape().rows][this.shape().columns];

        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < this.shape().columns; j++) {
                result[i][j] = this.get(i, j) * scalar;
            }
        }

        return new FullMatrix(result);
    }

    default IDoubleMatrix plus(IDoubleMatrix other) {
        assert this.shape().equals(other.shape());

        final double[][] result = new double[this.shape().rows][this.shape().columns];
        boolean isZero = true;

        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < this.shape().columns; j++) {
                result[i][j] = this.get(i, j) + other.get(i, j);
                if (result[i][j] != 0) isZero = false;
            }
        }

        if (isZero) return new ZeroMatrix(this.shape());
        return new FullMatrix(result);
    }

    default IDoubleMatrix plus(double scalar) {
        final double[][] result = new double[this.shape().rows][this.shape().columns];
        boolean isZero = true;

        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < this.shape().columns; j++) {
                result[i][j] = this.get(i, j) + scalar;
                if (result[i][j] != 0) isZero = false;
            }
        }

        if (isZero) return new ZeroMatrix(this.shape());
        return new FullMatrix(result);
    }

    default IDoubleMatrix minus(IDoubleMatrix other) {
        assert this.shape().equals(other.shape());

        final double[][] result = new double[this.shape().rows][this.shape().columns];
        boolean isZero = true;

        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < this.shape().columns; j++) {
                result[i][j] = this.get(i, j) - other.get(i, j);
                if (result[i][j] != 0) isZero = false;
            }
        }

        if (isZero) return new ZeroMatrix(this.shape());
        return new FullMatrix(result);
    }

    default IDoubleMatrix minus(double scalar) {
        final double[][] result = new double[this.shape().rows][this.shape().columns];
        boolean isZero = true;

        for (int i = 0; i < this.shape().rows; i++) {
            for (int j = 0; j < this.shape().columns; j++) {
                result[i][j] = this.get(i, j) - scalar;
                if (result[i][j] != 0) isZero = false;
            }
        }

        if (isZero) return new ZeroMatrix(this.shape());
        return new FullMatrix(result);
    }

    double get(int row, int column);

    double[][] data();

    double normOne();

    double normInfinity();

    double frobeniusNorm();

    String toString();

    Shape shape();
}
