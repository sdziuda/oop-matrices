package pl.edu.mimuw.matrix;

public class AntiDiagonalMatrix extends DiagonalIshMatrix{

    public AntiDiagonalMatrix(double... values) {
        super(values);
    }

    public IDoubleMatrix times(IDoubleMatrix other) {
        throw new UnsupportedOperationException("TODO");
    }

    public IDoubleMatrix plus(IDoubleMatrix other) {
        throw new UnsupportedOperationException("TODO");
    }

    public IDoubleMatrix plus(double scalar) {
        throw new UnsupportedOperationException("TODO");
    }

    public IDoubleMatrix minus(IDoubleMatrix other) {
        throw new UnsupportedOperationException("TODO");
    }

    public IDoubleMatrix minus(double scalar) {
        throw new UnsupportedOperationException("TODO");
    }

    public double get(int row, int column) {
        if (row + column == this.values.length) {
            return this.values[column];
        } else {
            return 0;
        }
    }

    public double[][] data() {
        final int n = this.values.length;
        double[][] data = new double[n][n];
        for (int i = 0; i < n; i++) {
            data[n - i - 1][i] = this.values[i];
        }
        return data;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("TODO");
    }
}
