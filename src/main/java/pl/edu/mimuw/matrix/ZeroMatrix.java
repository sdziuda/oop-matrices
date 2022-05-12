package pl.edu.mimuw.matrix;

public class ZeroMatrix implements IDoubleMatrix {
    private final Shape shape;

    public ZeroMatrix(Shape shape) {
        assert shape.rows > 0 && shape.columns > 0;

        this.shape = shape;
    }

    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
        return new ZeroMatrix(Shape.matrix(this.shape.rows, this.shape().columns));
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        return new ZeroMatrix(shape);
    }

    public double get(int row, int column) {
        this.shape.assertInShape(row, column);
        return 0;
    }

    public double[][] data() {
        return new double[this.shape.rows][this.shape.columns];
    }

    public double normOne() {
        return 0;
    }

    public double normInfinity() {
        return 0;
    }

    public double frobeniusNorm() {
        return 0;
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("TODO");
    }

    public Shape shape() {
        return this.shape;
    }

}
