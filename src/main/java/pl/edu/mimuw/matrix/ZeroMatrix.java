package pl.edu.mimuw.matrix;

public class ZeroMatrix implements IDoubleMatrix {
    private final Shape shape;

    public ZeroMatrix(Shape shape) {
        assert shape.rows > 0 && shape.columns > 0;

        this.shape = shape;
    }

    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
        assert this.shape.columns == other.shape().rows;

        return new ZeroMatrix(Shape.matrix(this.shape.rows, other.shape().columns));
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
        StringBuilder sb = new StringBuilder();

        sb.append("Zero matrix (").append(this.shape.rows).append("x").append(this.shape.columns).append("):\n");
        for (int i = 0; i < this.shape.rows; i++) {
            if (this.shape.columns >= 3) sb.append("0 ... 0");
            else {
                for (int j = 0; j < this.shape.columns; j++) {
                    sb.append("0.0");
                    if (j < this.shape.columns - 1) sb.append(" ");
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public Shape shape() {
        return this.shape;
    }

}
