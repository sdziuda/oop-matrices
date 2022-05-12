package pl.edu.mimuw.matrix;

public class AntiDiagonalMatrix extends DiagonalIshMatrix{

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
        throw new UnsupportedOperationException("TODO");
    }
}
