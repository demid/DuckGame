package com.game.util;

/**
 * Date: 20.04.12
 * Time: 17:38
 *
 * @author: Alexey
 */
public class Coordinate {
    public static double MAX_DELTA = 0.00000000000001;
    private double x;
    private double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Coordinate rotate(double angle) {
        Coordinate coordinate;
        if (angle != 0) {
            double cosA =  Math.cos(angle);
            double sinA =  Math.sin(angle);

            double nx = x * cosA - y * sinA;
            double ny = x * sinA + y * cosA;
            coordinate = new Coordinate(nx, ny);
        } else {
            coordinate = new Coordinate(x, y);
        }
        return coordinate;
    }

    public Coordinate rotateAndMove(double angle, double dx, double dy) {
        Coordinate coordinate = rotate(angle);
        coordinate.setX(coordinate.getX() + dx);
        coordinate.setY(coordinate.getY() + dy);
        return coordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinate)) return false;
        Coordinate that = (Coordinate) o;
        return ((Math.abs(that.x - x)) < MAX_DELTA && (Math.abs(that.y - y) < MAX_DELTA));
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}