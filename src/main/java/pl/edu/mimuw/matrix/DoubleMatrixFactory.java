package pl.edu.mimuw.matrix;

public class DoubleMatrixFactory {

    private DoubleMatrixFactory() {
    }

    public static IDoubleMatrix sparse(Shape shape, MatrixCellValue... values) {
        return new SparseMatrix(shape, values);
    }

    public static IDoubleMatrix full(double[][] values) {
        return new FullMatrix(values);
    }

    public static IDoubleMatrix identity(int size) {
        return new IdentityMatrix(size);
    }

    public static IDoubleMatrix diagonal(double... diagonalValues) {
        return new DiagonalMatrix(diagonalValues);
    }

    public static IDoubleMatrix antiDiagonal(double... antiDiagonalValues) {
        return new AntiDiagonalMatrix(antiDiagonalValues);
    }

    public static IDoubleMatrix vector(double... values) {
        return new Vector(values);
    }

    public static IDoubleMatrix zero(Shape shape) {
        return new ZeroMatrix(shape);
    }

    public static IDoubleMatrix constant(double value, Shape shape) {
        return new ConstMatrix(value, shape);
    }

    public static IDoubleMatrix row(Shape shape, double... values) {
        return new RowMatrix(shape, values);
    }

    public static IDoubleMatrix column(Shape shape, double... values) {
        return new ColumnMatrix(shape, values);
    }
}
