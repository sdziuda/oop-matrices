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
    }

    private SparseMatrix(Shape shape, List<List<MatrixCellValue>> values) {
        this.shape = shape;
        this.values = values;
    }

    @Override
    public IDoubleMatrix times(IDoubleMatrix other) {
        assert this.shape().columns == other.shape().rows;

        if (this.getClass() == other.getClass()) {
            final var otherMatrix = (SparseMatrix) other;

            final var resultShape = Shape.matrix(this.shape.rows, otherMatrix.shape.columns);
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

            final var usefulRows = rows.stream()
                    .map(list -> list.stream()
                            .sorted(COLUMN_COMPARATOR_FOR_ROWS)
                            .collect(Collectors.toList()))
                    .sorted(ROW_COMPARATOR_FOR_ROWS)
                    .collect(Collectors.toList());

            final List<MatrixCellValue> resultValues = new LinkedList<>();

            for (var row : usefulRows) {
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
                        resultValues.add(new MatrixCellValue(row.get(0).row, column.get(0).column, sum));
                    }
                }
            }

            return new SparseMatrix(resultShape, resultValues.toArray(new MatrixCellValue[0]));
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
                                .sorted(ROW_COMPARATOR_FOR_COLUMNS)
                                .collect(Collectors.toList()))
                        .sorted(COLUMN_COMPARATOR_FOR_COLUMNS)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public IDoubleMatrix plus(IDoubleMatrix other) {
        assert this.shape().equals(other.shape());

        if (this.getClass() == other.getClass()) {
            final var otherMatrix = (SparseMatrix) other;
            final var result = new LinkedList<List<MatrixCellValue>>();
            int thisColumnNumber = 0, otherColumnNumber = 0;

            while (thisColumnNumber < this.values.size() && otherColumnNumber < otherMatrix.values.size()) {
                final var thisColumn = this.values.get(thisColumnNumber);
                final var otherColumn = otherMatrix.values.get(otherColumnNumber);
                final var dataColumn = new LinkedList<MatrixCellValue>();

                if (thisColumn.get(0).column == otherColumn.get(0).column) {
                    int thisRowNumber = 0, otherRowNumber = 0;
                    while (thisRowNumber < thisColumn.size() && otherRowNumber < otherColumn.size()) {
                        final var thisValue = thisColumn.get(thisRowNumber);
                        final var otherValue = otherColumn.get(otherRowNumber);

                        if (thisValue.row == otherValue.row) {
                            dataColumn.add(new MatrixCellValue(thisValue.row, thisValue.column, thisValue.value + otherValue.value));
                            thisRowNumber++;
                            otherRowNumber++;
                        } else if (thisValue.row < otherValue.row) {
                            dataColumn.add(new MatrixCellValue(thisValue.row, thisValue.column, thisValue.value));
                            thisRowNumber++;
                        } else {
                            dataColumn.add(new MatrixCellValue(otherValue.row, otherValue.column, otherValue.value));
                            otherRowNumber++;
                        }
                    }

                    while (thisRowNumber < thisColumn.size()) {
                        final var thisValue = thisColumn.get(thisRowNumber);
                        dataColumn.add(new MatrixCellValue(thisValue.row, thisValue.column, thisValue.value));
                        thisRowNumber++;
                    }

                    while (otherRowNumber < otherColumn.size()) {
                        final var otherValue = otherColumn.get(otherRowNumber);
                        dataColumn.add(new MatrixCellValue(otherValue.row, otherValue.column, otherValue.value));
                        otherRowNumber++;
                    }
                } else if (thisColumn.get(0).column < otherColumn.get(0).column) {
                    int thisRowNumber = 0;
                    while (thisRowNumber < thisColumn.size()) {
                        final var thisValue = thisColumn.get(thisRowNumber);
                        dataColumn.add(new MatrixCellValue(thisValue.row, thisValue.column, thisValue.value));
                        thisRowNumber++;
                    }
                } else {
                    int otherRowNumber = 0;
                    while (otherRowNumber < otherColumn.size()) {
                        final var otherValue = otherColumn.get(otherRowNumber);
                        dataColumn.add(new MatrixCellValue(otherValue.row, otherValue.column, otherValue.value));
                        otherRowNumber++;
                    }
                }

                result.add(dataColumn);
                thisColumnNumber++;
                otherColumnNumber++;
            }

            while (thisColumnNumber < this.values.size()) {
                final var thisColumn = this.values.get(thisColumnNumber);
                final var dataColumn = new LinkedList<MatrixCellValue>();
                int thisRowNumber = 0;
                while (thisRowNumber < thisColumn.size()) {
                    final var thisValue = thisColumn.get(thisRowNumber);
                    dataColumn.add(new MatrixCellValue(thisValue.row, thisValue.column, thisValue.value));
                    thisRowNumber++;
                }
                result.add(dataColumn);
                thisColumnNumber++;
            }

            while (otherColumnNumber < otherMatrix.values.size()) {
                final var otherColumn = otherMatrix.values.get(otherColumnNumber);
                final var dataColumn = new LinkedList<MatrixCellValue>();
                int otherRowNumber = 0;
                while (otherRowNumber < otherColumn.size()) {
                    final var otherValue = otherColumn.get(otherRowNumber);
                    dataColumn.add(new MatrixCellValue(otherValue.row, otherValue.column, otherValue.value));
                    otherRowNumber++;
                }
                result.add(dataColumn);
                otherColumnNumber++;
            }

            return new SparseMatrix(this.shape(), result);
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
            boolean dots = false;
            for (int column = 0; column < row.length; column++) {
                if (column > 0 && column < row.length - 1 && row[column] == row[column - 1]
                        && row[column] == row[column + 1] && !dots) {

                    sb.append("... ");
                    dots = true;
                } else if (column == 0 || column == row.length - 1 || row[column] != row[column - 1]
                        || row[column] != row[column + 1]) {

                    sb.append(BigDecimal.valueOf(row[column]).setScale(1, RoundingMode.HALF_UP)).append(" ");
                    dots = false;
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
