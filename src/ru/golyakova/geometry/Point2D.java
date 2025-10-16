package ru.golyakova.geometry;

public class Point2D {
    private final double x;
    private final double y;

    public final double getX() {
        return x;
    }

    public final double getY() {
        return y;
    }

    public Point2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "{" + x + ";" + y + "}";
    }
}
