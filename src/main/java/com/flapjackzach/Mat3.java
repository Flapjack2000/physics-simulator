package com.flapjackzach;
import java.util.Arrays;
import java.lang.Math;

public final class Mat3 {

    private final double[] entries;

    public static Mat3 IDENTITY = new Mat3(new double[]{
            1, 0, 0,
            0, 1, 0,
            0, 0, 1
    });

    public static Mat3 ZERO = new Mat3(new double[]{
            0, 0, 0,
            0, 0, 0,
            0, 0, 0
    });

    private Mat3(double[] entries) {
        if (entries.length != 9) {
            throw new IllegalArgumentException(
                    "Mat3 requires exactly 9 elements, got " + entries.length
            );
        }
        this.entries = entries;
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

    public Mat3 rotation(double radians) {
        return new Mat3(new double[]{
                Math.cos(radians), -Math.sin(radians), 0,
                Math.sin(radians), Math.cos(radians), 0,
                0, 0, 1
        });
    }

    public Mat3 rotationDegrees(double degrees) {
        return rotation( Math.PI * degrees / 180);
    }
}
