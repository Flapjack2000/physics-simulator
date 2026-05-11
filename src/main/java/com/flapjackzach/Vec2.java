package com.flapjackzach;

public record Vec2(double x, double y) {

    public static Vec2 ZERO = new Vec2(0, 0);
    public static Vec2 ONE = new Vec2(1, 1);
    public static Vec2 DOWN = new Vec2(0, 1);
    public static Vec2 UP = new Vec2(0, -1);
    public static Vec2 RIGHT = new Vec2(1, 0);
    public static Vec2 LEFT = new Vec2(-1, 0);

    public Vec2 add(Vec2 other) {
        return new Vec2(x + other.x, y + other.y);
    }

    public Vec2 subtract(Vec2 other) {
        return new Vec2(x - other.x, y - other.y);
    }

    public Vec2 negate() {
        return new Vec2(-x, -y);
    }

    public boolean equals(Vec2 other) {
        return x == other.x && y == other.y;
    }

    public boolean isZero() {
        return x == 0 && y == 0;
    }

    public double dot(Vec2 other) {
        return x * other.x + y * other.y;
    }

    public double cross(Vec2 other) {
        return x * other.y - other.x * y;
    }

    public Vec2 scale(double scalar) {
        return new Vec2(x * scalar, y * scalar);
    }

    public double magnitude() {
        return Math.sqrt((x * x) + (y * y));
    }

    public double angleBetween(Vec2 other) {
        if (isZero() || other.isZero()) {
            throw new IllegalStateException(
                    "Computing the angle between a vector " +
                            "and the zero vector doesn't make any sense."
            );
        }
        double cosAngle = dot(other) / (magnitude() * other.magnitude());
        return Math.acos(Math.clamp(cosAngle, -1.0, 1.0));
    }

    public Vec2 normalize() {
        if (isZero()) {
            throw new IllegalStateException("Cannot normalize a zero vector.");
        }
        double magnitude = magnitude();
        return new Vec2(x / magnitude, y / magnitude);
    }
}
