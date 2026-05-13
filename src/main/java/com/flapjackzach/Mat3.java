package com.flapjackzach;

import java.util.Arrays;
import java.lang.Math;

public final class Mat3 {

    private final double[] entries;

    public double[] getEntries() {
        return Arrays.copyOf(entries, entries.length);
    }

    private Mat3(double[] entries) {
        if (entries.length != 9) {
            throw new IllegalArgumentException(
                    "Mat3 requires exactly 9 elements, got " + entries.length
            );
        }
        this.entries = entries;
    }

    public final static Mat3 IDENTITY = new Mat3(new double[]{
            1, 0, 0,
            0, 1, 0,
            0, 0, 1
    });

    public final static Mat3 ZERO = new Mat3(new double[]{
            0, 0, 0,
            0, 0, 0,
            0, 0, 0
    });

    public final static Mat3 ONE = new Mat3(new double[]{
            1, 1, 1,
            1, 1, 1,
            1, 1, 1
    });

    public Vec2 getXAxis() {
        return new Vec2(entries[0], entries[3]);
    }

    public Vec2 getYAxis() {
        return new Vec2(entries[1], entries[4]);
    }

    public static Mat3 fromTranslation(Vec2 vector) {
        return new Mat3(new double[]{
                1, 0, vector.x(),
                0, 1, vector.y(),
                0, 0, 1
        });
    }

    public static Mat3 fromTranslation(double x, double y) {
        return fromTranslation(new Vec2(x, y));
    }

    public Vec2 getTranslation() {
        return new Vec2(entries[2], entries[5]);
    }

    public static Mat3 fromRotation(double radians) {
        return new Mat3(new double[]{
                Math.cos(radians), -Math.sin(radians), 0,
                Math.sin(radians), Math.cos(radians), 0,
                0, 0, 1
        });
    }

    public static Mat3 fromRotationDegrees(double degrees) {
        return fromRotation(Math.PI * degrees / 180);
    }

    public double getRotation() {
        Vec2 x = getXAxis();
        Vec2 y = getYAxis();

        if (Math.abs(x.dot(y) / (x.magnitude() * y.magnitude())) > 1e-9) {
            throw new IllegalStateException(
                    "Matrix has skew and cannot be cleanly decomposed."
            );
        }

        double scaleX = x.magnitude();
        return Math.atan2(x.y() / scaleX, x.x() / scaleX);
    }

    public static Mat3 fromScale(double scaleX, double scaleY) {
        return new Mat3(new double[]{
                scaleX, 0, 0,
                0, scaleY, 0,
                0, 0, 1
        });
    }

    public static Mat3 fromScale(double scale) {
        return fromScale(scale, scale);
    }

    public Vec2 getScale() {
        return new Vec2(
                getXAxis().magnitude(),
                getYAxis().magnitude()
        );
    }

    public static Mat3 fromTRS(Vec2 position, double rotation, Vec2 scale) {
        double cos = Math.cos(rotation);
        double sin = Math.sin(rotation);
        return new Mat3(new double[]{
                cos * scale.x(), -sin * scale.y(), position.x(),
                sin * scale.x(), cos * scale.y(), position.y(),
                0, 0, 1
        });
    }

    public boolean equals(Mat3 other) {
        return Arrays.equals(entries, other.entries);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Mat3 other)) return false;
        return Arrays.equals(entries, other.entries);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(entries);
    }

    public Mat3 scaleEntries(double scalar) {
        double[] scaled = new double[9];
        for (int i = 0; i < 9; i++) {
            scaled[i] = entries[i] * scalar;
        }
        return new Mat3(scaled);
    }

    public Mat3 transpose() {
        double[] transpose = new double[9];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                transpose[i * 3 + j] = entries[j * 3 + i];
        return new Mat3(transpose);
    }

    public double determinant() {
        return (
                entries[0] * (entries[4] * entries[8] - entries[5] * entries[7])
                        - entries[1] * (entries[3] * entries[8] - entries[5] * entries[6])
                        + entries[2] * (entries[3] * entries[7] - entries[4] * entries[6])
        );
    }

    public boolean isInvertible() {
        return Math.abs(determinant()) > 1e-9;
    }

    public Mat3 inverse() {

        double det = determinant();

        if (Math.abs(det) < 1e-9) {
            throw new IllegalArgumentException("Matrix is not invertible (determinant is zero).");
        }

        // Method of cofactors
        double[] cofactor = new double[]{
                ((entries[4] * entries[8]) - (entries[5] * entries[7])),
                -((entries[3] * entries[8]) - (entries[5] * entries[6])),
                ((entries[3] * entries[7]) - (entries[4] * entries[6])),
                -((entries[1] * entries[8]) - (entries[2] * entries[7])),
                ((entries[0] * entries[8]) - (entries[2] * entries[6])),
                -((entries[0] * entries[7]) - (entries[1] * entries[6])),
                ((entries[1] * entries[5]) - (entries[2] * entries[4])),
                -((entries[0] * entries[5]) - (entries[2] * entries[3])),
                ((entries[0] * entries[4]) - (entries[1] * entries[3]))
        };
        return new Mat3(cofactor)
                .transpose()
                .scaleEntries(1.0 / det);
    }

    public Mat3 multiply(Mat3 right) {
        double[] product = new double[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    product[i * 3 + j] += entries[i * 3 + k] * right.entries[j + k * 3];
                }
            }
        }
        return new Mat3(product);
    }

    public Vec2 transformPoint(Vec2 right) {
        return new Vec2(
                entries[0] * right.x() + entries[1] * right.y() + entries[2],
                entries[3] * right.x() + entries[4] * right.y() + entries[5]
        );
    }

    public Vec2 transformDirection(Vec2 right) {
        return new Vec2(
                entries[0] * right.x() + entries[1] * right.y(),
                entries[3] * right.x() + entries[4] * right.y()
        );
    }

    @Override
    public String toString() {
        return String.format("[%.2f, %.2f, %.2f]\n[%.2f, %.2f, %.2f]\n[%.2f, %.2f, %.2f]",
                entries[0], entries[1], entries[2],
                entries[3], entries[4], entries[5],
                entries[6], entries[7], entries[8]
        );
    }
}
