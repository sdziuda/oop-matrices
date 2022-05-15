package pl.edu.mimuw;

import pl.edu.mimuw.matrix.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Main {

    private static final int N = 10;

    //IDE: IntelliJ IDEA 2021.3.3
    public static void main(String[] args) {

        System.out.println("Representations of matrices:\n");

        final var vector = new Vector(randomValues());
        System.out.println(vector);

        final var fullMatrix = new FullMatrix(random2dValues());
        System.out.println(fullMatrix);

        final var zeroMatrix = new ZeroMatrix(Shape.matrix(N, N));
        System.out.println(zeroMatrix);

        final var constMatrix = new ConstMatrix(Math.round(Math.random() * 100) / 10.0, Shape.matrix(N, N));
        System.out.println(constMatrix);

        final var rowMatrix = new RowMatrix(Shape.matrix(N, N), randomValues());
        System.out.println(rowMatrix);

        final var columnMatrix = new ColumnMatrix(Shape.matrix(N, N), randomValues());
        System.out.println(columnMatrix);

        final var diagonalMatrix = new DiagonalMatrix(randomValues());
        System.out.println(diagonalMatrix);

        final var antiDiagonalMatrix = new AntiDiagonalMatrix(randomValues());
        System.out.println(antiDiagonalMatrix);

        final var identityMatrix = new IdentityMatrix(N);
        System.out.println(identityMatrix);

        final var sparseMatrix = new SparseMatrix(Shape.matrix(N, N), MatrixCellValue.cell(1, 2, 4.2),
                MatrixCellValue.cell(6, 9, 2.2), MatrixCellValue.cell(5, 5, 0.5));
        System.out.println(sparseMatrix);

        System.out.println("\nOperations on two matrices:\n");
        System.out.println("In this section we will use two full matrices:");

        final var matrix1 = new FullMatrix(random2dValues());
        System.out.println(matrix1);
        final var matrix2 = new FullMatrix(random2dValues());
        System.out.println(matrix2);

        System.out.println("Matrix addition:");
        System.out.println(matrix1.plus(matrix2));

        System.out.println("Matrix subtraction:");
        System.out.println(matrix1.minus(matrix2));

        System.out.println("Matrix multiplication:");
        System.out.println(matrix1.times(matrix2));

        System.out.println("\nOperations on one matrix:\n");

        final double scalar = Math.round(Math.random() * 100) / 10.0;

        System.out.println("In this section we will use one full matrix (and scalar " + scalar + "):");

        final var matrix3 = new FullMatrix(random2dValues());
        System.out.println(matrix3);

        System.out.println("Matrix plus scalar:");
        System.out.println(matrix3.plus(scalar));

        System.out.println("Matrix minus scalar:");
        System.out.println(matrix3.minus(scalar));

        System.out.println("Matrix times scalar:");
        System.out.println(matrix3.times(scalar));

        System.out.println("One-norm of matrix:");
        System.out.println(BigDecimal.valueOf(matrix3.normOne()).setScale(2, RoundingMode.HALF_UP));

        System.out.println("Infinity-norm of matrix:");
        System.out.println(BigDecimal.valueOf(matrix3.normInfinity()).setScale(2, RoundingMode.HALF_UP));

        System.out.println("Frobenius norm of matrix:");
        System.out.println(BigDecimal.valueOf(matrix3.frobeniusNorm()).setScale(2, RoundingMode.HALF_UP));


    }

    private static double[] randomValues() {
        final double[] array = new double[N];

        for (int i = 0; i < N; i++) {
            array[i] = Math.round(Math.random() * 100) / 10.0;
            if (array[i] == 10.0) array[i]--;
        }

        return array;
    }

    private static double[][] random2dValues() {
        final double[][] array = new double[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                array[i][j] = Math.round(Math.random() * 100) / 10.0;
                if (array[i][j] == 10.0) array[i][j]--;
            }
        }

        return array;
    }
}
