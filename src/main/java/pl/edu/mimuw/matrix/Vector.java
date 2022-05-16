package pl.edu.mimuw.matrix;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

public class Vector extends ColumnMatrix {

    public Vector(double... values) {
        super(Shape.vector(values.length), values);
    }

    @Override
    public IDoubleMatrix times(double scalar) {
        final double[] result = new double[this.values.length];

        for (int i = 0; i < this.values.length; i++) {
            result[i] = this.values[i] * scalar;
        }

        return new Vector(result);
    }

    @Override
    public IDoubleMatrix plus(IDoubleMatrix other) {
        assert this.shape().equals(other.shape());

        final double[] result = new double[this.values.length];

        for (int i = 0; i < this.values.length; i++) {
            result[i] = this.values[i] + other.get(i, 0);
        }

        return new Vector(result);
    }

    @Override
    public IDoubleMatrix plus(double scalar) {
        final double[] result = new double[this.values.length];

        for (int i = 0; i < this.values.length; i++) {
            result[i] = this.values[i] + scalar;
        }

        return new Vector(result);
    }

    @Override
    public IDoubleMatrix minus(IDoubleMatrix other) {
        assert this.shape().equals(other.shape());

        final double[] result = new double[this.values.length];

        for (int i = 0; i < this.values.length; i++) {
            result[i] = this.values[i] - other.get(i, 0);
        }

        return new Vector(result);
    }

    @Override
    public IDoubleMatrix minus(double scalar) {
        final double[] result = new double[this.values.length];

        for (int i = 0; i < this.values.length; i++) {
            result[i] = this.values[i] - scalar;
        }

        return new Vector(result);
    }

    @Override
    public String toString() {
        final var sb = new StringBuilder();

        sb.append("Vector (").append(this.values.length).append("x1):\n");
        Arrays.stream(this.values)
                .forEach(value -> sb.append(BigDecimal.valueOf(value).setScale(1, RoundingMode.HALF_UP))
                        .append("\n"));

        return sb.toString();
    }
}