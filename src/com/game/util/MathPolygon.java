package com.game.util;

/**
 * Date: 30.04.12
 * Time: 10:25
 *
 * @author: Alexey
 */
public class MathPolygon {
    private MathLine[] mathLines;
    private double minX = Double.MAX_VALUE, maxX = Double.MIN_VALUE;

    public MathPolygon(Coordinate... coordinates) {
        if (coordinates.length < 1) {
            throw new IllegalArgumentException("Number of coordinates should be great than 0.");
        }
        mathLines = new MathLine[coordinates.length];
        for (int i = 0; i < coordinates.length - 1; i++) {
            mathLines[i] = new MathLine(coordinates[i], coordinates[i + 1]);
            if (coordinates[i].getX() < minX) {
                minX = coordinates[i].getX();
            }
            if (coordinates[i].getX() > maxX) {
                maxX = coordinates[i].getX();
            }
        }
        mathLines[coordinates.length - 1] = new MathLine(coordinates[coordinates.length - 1], coordinates[0]);
        if (coordinates[coordinates.length - 1].getX() < minX) {
            minX = coordinates[coordinates.length - 1].getX();
        }
        if (coordinates[coordinates.length - 1].getX() > maxX) {
            maxX = coordinates[coordinates.length - 1].getX();
        }
    }

    public boolean inPolygon(Coordinate coordinate) {
        if (coordinate == null) {
            throw new IllegalArgumentException(new NullPointerException("'coordinate' can't be null."));
        }
        MathLine leftHorizontal = new MathLine(new Coordinate(minX - Coordinate.MAX_DELTA, coordinate.getY()), new Coordinate(coordinate.getX(), coordinate.getY()));
        MathLine rightHorizontal = new MathLine(new Coordinate(maxX + Coordinate.MAX_DELTA, coordinate.getY()), new Coordinate(coordinate.getX(), coordinate.getY()));

        int leftIntersections = 0, rightIntersections = 0;
        for (MathLine mathLine : mathLines) {
            if (mathLine.isOnLine(coordinate)) {
                return true;
            }
            if (mathLine.getLineIntersection(leftHorizontal) != null) {
                leftIntersections++;
            }
            if (mathLine.getLineIntersection(rightHorizontal) != null) {
                rightIntersections++;
            }
        }
        if ((leftIntersections == 0) || (rightIntersections == 0)) {
            return false;
        }
        if (((leftIntersections % 2) == 1) && ((rightIntersections % 2) == 1)) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isNear(Coordinate coordinate, double maxDistance) {
        if (maxDistance < 0) {
            throw new IllegalArgumentException("'maxDistance' can't be negative");
        }
        for (MathLine mathLine : mathLines) {
            if (mathLine.getDistance(coordinate) <= maxDistance) {
                return true;
            }
        }
        return false;
    }

    public MathLine[] getMathLines() {
        return mathLines;
    }
}
