package pl.edu.mimuw.matrix;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.*;

public class SparseMatrix implements IDoubleMatrix {

    private static final Comparator<MatrixCellValue> ROW_COMPARATOR_FOR_COLUMNS = Comparator.comparingInt(v -> v.row);
    private static final Comparator<List<MatrixCellValue>> COLUMN_COMPARATOR_FOR_COLUMNS = Comparator.comparingInt(v -> v.get(0).column);
    private static final Comparator<List<MatrixCellValue>> ROW_COMPARATOR_FOR_ROWS = Comparator.comparingInt(v -> v.get(0).row);
    private static final Comparator<MatrixCellValue> COLUMN_COMPARATOR_FOR_ROWS = Comparator.comparingInt(v -> v.column);

    private final Shape shape;
    private final List<List<MatrixCellValue>> values;

    public SparseMatrix(Shape shape, MatrixCellValue... values) {
        assert shape.rows > 0 && shape.columns > 0;

        this.shape = shape;

        assert values != null;
        for (var value : values) {
            shape.assertInShape(value.row, value.column);
        }

        List<List<MatrixCellValue>> result = new LinkedList<>();
        for (var value : values) {
            boolean found = false;

            for (List<MatrixCellValue> matrixCellValues : result) {
                if (matrixCellValues.get(0).column == value.column) {
                    found = true;
                    matrixCellValues.add(value);
                    break;
                }
            }

            if (!found) {
                result.add(new LinkedList<>());
                result.get(result.size() - 1).add(value);
            }
        }

        this.values = result.stream()
                .map(list -> list.stream()
                        .sorted(ROW_COMPARATOR_FOR_COLUMNS)
                        .collect(Collectors.toList()))
                .sorted(COLUMN_COMPARATOR_FOR_COLUMNS)
                .collect(Collectors.toList());

        for (var column : this.values) {
            for (int i = 0; i < column.size() - 1; i++) {
                assert column.get(i).row != column.get(i + 1).row;
            }
        }
    }

    private SparseMatrix(Shape shape, List<List<MatrixCellValue>> values) {
        assert shape.rows > 0 && shape.columns > 0;

        this.shape = shape;

        assert values != null;
        for (var column : values) {
            for (var value : column) {
                shape.assertInShape(value.row, value.column);
            }

            for (int i = 0; i < column.size() - 1; i++) {
                assert column.get(i).row != column.get(i + 1).row;
            }
        }

        this.values = values.stream()
                .map(list -> list.stream()
                        .map(value -> new MatrixCellValue(value.row, value.column, value.value))
                        .sorted(ROW_COMPARATOR_FOR_COLUMNS)
                        .collect(Collectors.toList()))
                .sorted(COLUMN_COMPARATOR_FOR_COLUMNS)
                .collect(Collectors.toList());
    }

    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
        assert this.shape().columns == other.shape().rows;

        if (this.getClass() == other.getClass()) {
            return new SparseMatrix(Shape.matrix(this.shape.rows, other.shape().columns),
                    this.getSparseMultiplicationResult((SparseMatrix) other).toArray(new MatrixCellValue[0]));
        } else {
            return IDoubleMatrix.super.times(other);
        }
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        return new SparseMatrix(this.shape,
                this.values.stream()
                        .map(list -> list.stream()
                                .map(value -> new MatrixCellValue(value.row, value.column, value.value * scalar))
                                .collect(Collectors.toList()))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public IDoubleMatrix plus(IDoubleMatrix other) {
        assert this.shape().equals(other.shape());

        if (this.getClass() == other.getClass()) {
            return new SparseMatrix(this.shape(), this.getSparseAdditionResult((SparseMatrix) other));
        } else {
            return IDoubleMatrix.super.plus(other);
        }
    }

    @Override
    public IDoubleMatrix minus(IDoubleMatrix other) {
        return this.plus(other.times(-1));
    }

    public double get(int row, int column) {
        this.shape.assertInShape(row, column);

        for (var currColumn : this.values) {
            if (currColumn.size() == 0 || currColumn.get(0).column != column) continue;

            for (var currValue : currColumn) {
                if (currValue.row == row) return currValue.value;
            }
        }

        return 0;
    }

    public double[][] data() {
        final double[][] result = new double[this.shape().rows][this.shape().columns];

        for (var column : this.values) {
            for (var value : column) {
                result[value.row][value.column] = value.value;
            }
        }

        return result;
    }

    public double normOne() {
        final double[] columnSums = new double[this.shape().columns];

        for (var column : this.values) {
            for (var value : column) {
                columnSums[value.column] += abs(value.value);
            }
        }

        return Arrays.stream(columnSums).max().isPresent() ? Arrays.stream(columnSums).max().getAsDouble() : 0;
    }

    public double normInfinity() {
        final double[] rowSums = new double[this.shape().rows];

        for (var column : this.values) {
            for (var value : column) {
                rowSums[value.row] += abs(value.value);
            }
        }

        return Arrays.stream(rowSums).max().isPresent() ? Arrays.stream(rowSums).max().getAsDouble() : 0;
    }

    public double frobeniusNorm() {
        double result = 0;

        for (var column : this.values) {
            for (var value : column) {
                result += value.value * value.value;
            }
        }

        return sqrt(result);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final double[][] data = this.data();

        sb.append("Sparse matrix (").append(this.shape().rows).append("x").append(this.shape().columns).append("):\n");
        for (double[] row : data) {
            for (double v : row) {
                sb.append(BigDecimal.valueOf(v).setScale(1, RoundingMode.HALF_UP)).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public Shape shape() {
        return this.shape;
    }

    /**
     * Creates a list of lists of (all non-zero) values stored in the matrix, but a single list stores all values of a
     * single row instead of a single column.
     * @return a linked list of linked lists containing values of the matrix ordered by rows.
     */
    private List<List<MatrixCellValue>> getConvertedToListOfRows() {
        final var rows = new LinkedList<List<MatrixCellValue>>();

        for (var column : this.values) {
            for (var value : column) {
                boolean found = false;
                final var valueCopy = new MatrixCellValue(value.row, value.column, value.value);
                for (List<MatrixCellValue> row : rows) {
                    if (row.get(0).row == value.row) {
                        found = true;
                        row.add(valueCopy);
                        break;
                    }
                }
                if (!found) {
                    rows.add(new LinkedList<>());
                    rows.get(rows.size() - 1).add(valueCopy);
                }
            }
        }

        return rows.stream()
                .map(list -> list.stream()
                        .sorted(COLUMN_COMPARATOR_FOR_ROWS)
                        .collect(Collectors.toList()))
                .sorted(ROW_COMPARATOR_FOR_ROWS)
                .collect(Collectors.toList());
    }

    /**
     * @param otherMatrix the matrix by which this matrix is multiplied.
     * @return linked list of values after multiplication of two sparse matrices.
     */
    private List<MatrixCellValue> getSparseMultiplicationResult(SparseMatrix otherMatrix) {
        final List<List<MatrixCellValue>> rows = this.getConvertedToListOfRows();
        final List<MatrixCellValue> result = new LinkedList<>();

        for (var row : rows) {
            for (var column : otherMatrix.values) {
                int i = 0, j = 0;
                double sum = 0;

                while (i < row.size() && j < column.size()) {
                    if (row.get(i).column < column.get(j).row) {
                        i++;
                    } else if (row.get(i).column > column.get(j).row) {
                        j++;
                    } else if (row.get(i).column == column.get(j).row) {
                        sum += row.get(i).value * column.get(j).value;
                        i++;
                        j++;
                    }
                }

                if (sum != 0) {
                    result.add(new MatrixCellValue(row.get(0).row, column.get(0).column, sum));
                }
            }
        }

        return result;
    }

    /**
     * @param other the matrix we want to add to this matrix.
     * @return linked list of values after addition of two sparse matrices.
     */
    private List<List<MatrixCellValue>> getSparseAdditionResult(SparseMatrix other) {
        final var result = new LinkedList<List<MatrixCellValue>>();
        int thisColumnNumber = 0, otherColumnNumber = 0;

        while (thisColumnNumber < this.values.size() && otherColumnNumber < other.values.size()) {
            final var thisColumn = this.values.get(thisColumnNumber);
            final var otherColumn = other.values.get(otherColumnNumber);
            final var resultColumn = new LinkedList<MatrixCellValue>();

            if (thisColumn.get(0).column == otherColumn.get(0).column) {
                addCorrespondingColumn(thisColumn, otherColumn, resultColumn);
            } else if (thisColumn.get(0).column < otherColumn.get(0).column) {
                addRestOfColumn(thisColumn, resultColumn, 0);
            } else {
                addRestOfColumn(otherColumn, resultColumn, 0);
            }

            result.add(resultColumn);
            thisColumnNumber++;
            otherColumnNumber++;
        }

        addRestOfMatrix(result, this, thisColumnNumber);

        addRestOfMatrix(result, other, otherColumnNumber);

        return result;
    }

    /**
     * Adds rest of the given column to the result column.
     * @param column the column we want to add to the result column.
     * @param result the result column.
     * @param index  the index of starting point.
     */
    private static void addRestOfColumn(List<MatrixCellValue> column, List<MatrixCellValue> result, int index) {
        while (index < column.size()) {
            final var thisValue = column.get(index);
            result.add(new MatrixCellValue(thisValue.row, thisValue.column, thisValue.value));
            index++;
        }
    }

    /**
     * Adds the remaining columns of the given matrix to the result list.
     * @param result the list we want to add to.
     * @param matrix the matrix we want to add the rest of.
     * @param columnNumber the number of the column we want to start from.
     */
    private static void addRestOfMatrix(List<List<MatrixCellValue>> result, SparseMatrix matrix, int columnNumber) {
        while (columnNumber < matrix.values.size()) {
            final var thisColumn = matrix.values.get(columnNumber);
            final var dataColumn = new LinkedList<MatrixCellValue>();

            addRestOfColumn(thisColumn, dataColumn, 0);

            result.add(dataColumn);
            columnNumber++;
        }
    }

    /**
     * Adds the corresponding columns to the result column.
     * @param column the column we want to add to the result column.
     * @param other the other column we want to add to the result column.
     * @param result the result column.
     */
    private static void addCorrespondingColumn(List<MatrixCellValue> column, List<MatrixCellValue> other,
                                               List<MatrixCellValue> result) {
        int thisRowNumber = 0, otherRowNumber = 0;

        while (thisRowNumber < column.size() && otherRowNumber < other.size()) {
            final var thisValue = column.get(thisRowNumber);
            final var otherValue = other.get(otherRowNumber);

            if (thisValue.row == otherValue.row) {
                result.add(new MatrixCellValue(thisValue.row, thisValue.column, thisValue.value + otherValue.value));
                thisRowNumber++;
                otherRowNumber++;
            } else if (thisValue.row < otherValue.row) {
                result.add(new MatrixCellValue(thisValue.row, thisValue.column, thisValue.value));
                thisRowNumber++;
            } else {
                result.add(new MatrixCellValue(otherValue.row, otherValue.column, otherValue.value));
                otherRowNumber++;
            }
        }

        addRestOfColumn(column, result, thisRowNumber);

        addRestOfColumn(other, result, otherRowNumber);
    }
}
