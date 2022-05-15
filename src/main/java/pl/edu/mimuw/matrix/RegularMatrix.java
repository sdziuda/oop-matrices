package pl.edu.mimuw.matrix;

public abstract class RegularMatrix implements IDoubleMatrix {
    protected final double[] values;
    protected final Shape shape;

    protected RegularMatrix(Shape shape, double... values) {
        assert shape.rows > 0 && shape.columns > 0;

        this.shape = shape;
        this.values = new double[values.length];
        System.arraycopy(values, 0, this.values, 0, values.length);
    }

    public Shape shape() {
        return this.shape;
    }
}
