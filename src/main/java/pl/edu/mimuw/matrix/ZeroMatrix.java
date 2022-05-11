package pl.edu.mimuw.matrix;

public class ZeroMatrix implements IDoubleMatrix {
    private final Shape shape;

    public ZeroMatrix(Shape shape) {
        this.shape = shape;
    }

    public IDoubleMatrix times(IDoubleMatrix other) {
        return new ZeroMatrix(shape);
    }

    public IDoubleMatrix times(double scalar) {
        return new ZeroMatrix(shape);
    }

    public IDoubleMatrix plus(IDoubleMatrix other) {
        return other;
    }

    public IDoubleMatrix plus(double scalar) {
        throw new UnsupportedOperationException("TODO");
    }

    public IDoubleMatrix minus(IDoubleMatrix other) {
        return other.times(-1);
    }

    public IDoubleMatrix minus(double scalar) {
        throw new UnsupportedOperationException("TODO");
    }

    public double get(int row, int column) {
        return 0;
    }

    public double[][] data() {
        return new double[][] {};
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
