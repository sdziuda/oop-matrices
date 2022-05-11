package pl.edu.mimuw.matrix;

public class DiagonalMatrix extends DiagonalIshMatrix{

    public DiagonalMatrix(double[] values) {
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
        return this.plus(-scalar);
    }

    public double get(int i, int j) {
        if (i == j) {
            return values[i];
        } else {
            return 0;
        }
    }

    public double[][] data() {
        double[][] result = new double[values.length][values.length];
        for (int i = 0; i < values.length; i++) {
            result[i][i] = values[i];
        }
        return result;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("TODO");
    }
}
