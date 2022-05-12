package pl.edu.mimuw.matrix;

public class DiagonalMatrix extends DiagonalIshMatrix{

    public DiagonalMatrix(double[] values) {
        super(values);
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        final double[] result = new double[this.values.length];

        for (int i = 0; i < this.values.length; i++) {
            result[i] = this.values[i] * scalar;
        }

        return new DiagonalMatrix(result);
    }

    public double get(int row, int column) {
        this.shape().assertInShape(row, column);

        if (row == column) {
            return this.values[row];
        } else {
            return 0;
        }
    }

    public double[][] data() {
        final double[][] result = new double[this.values.length][this.values.length];
        for (int i = 0; i < this.values.length; i++) {
            result[i][i] = this.values[i];
        }
        return result;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("TODO");
    }
}
