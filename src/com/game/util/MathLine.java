package com.game.util;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Date: 20.04.12
 * Time: 17:39
 *
 * @author: Alexey
 */
public class MathLine {
    private Coordinate start;
    private Coordinate end;

    public MathLine(Coordinate start, Coordinate end) {
        if (start == null) {
            throw new IllegalArgumentException(new NullPointerException("'start' can't be null."));
        }
        if (end == null) {
            throw new IllegalArgumentException(new NullPointerException("'end' can't be null."));
        }
        this.start = start;
        this.end = end;
    }

    public Coordinate getLineIntersection(MathLine line) {
        return getLineIntersection(line, true);
    }


    public boolean isParallel(MathLine line) {
        if (line == null) {
            throw new IllegalArgumentException(new NullPointerException("'line' can't be null."));
        }
        double dx1 = getEnd().getX() - getStart().getX();
        double dy1 = getEnd().getY() - getStart().getY();
        double dx2 = line.getEnd().getX() - line.getStart().getX();
        double dy2 = line.getEnd().getY() - line.getStart().getY();
        double d1 = Math.abs(dx1 / dy1);
        double d2 = Math.abs(dx2 / dy2);
        if (Math.abs(d2 - d1) > Coordinate.MAX_DELTA) {
            return false;
        } else {
            return true;
        }

    }

    public Coordinate getLineIntersection(MathLine line, boolean checkBounds) {
        if (line == null) {
            throw new IllegalArgumentException(new NullPointerException("'line' can't be null."));
        }
        double xD1, yD1, xD2, yD2, xD3, yD3;
        double dot, deg, len1, len2;
        double segmentLen1, segmentLen2;
        double ua, ub, div;

        // calculate differences
        xD1 = end.getX() - start.getX();
        xD2 = line.getEnd().getX() - line.getStart().getX();
        yD1 = end.getY() - start.getY();
        yD2 = line.getEnd().getY() - line.getStart().getY();
        xD3 = start.getX() - line.getStart().getX();
        yD3 = start.getY() - line.getStart().getY();


        // calculate the lengths of the two lines
        len1 = Math.sqrt(xD1 * xD1 + yD1 * yD1);
        len2 = Math.sqrt(xD2 * xD2 + yD2 * yD2);

        // calculate angle between the two lines.
        dot = (xD1 * xD2 + yD1 * yD2); // dot product
        deg = dot / (len1 * len2);

        // if abs(angle)==1 then the lines are parallell,
        // so no intersection is possible
        double df = (1 - Math.abs(deg));
        if (df < Coordinate.MAX_DELTA) return null;

        // find intersection Pt between two lines
        Coordinate pt = new Coordinate(0, 0);
        div = yD2 * xD1 - xD2 * yD1;
        ua = (xD2 * yD3 - yD2 * xD3) / div;
        ub = (xD1 * yD3 - yD1 * xD3) / div;
        pt.setX(start.getX() + ua * xD1);
        pt.setY(start.getY() + ua * yD1);

        if (checkBounds) {
            // calculate the combined length of the two segments
            // between Pt-p1 and Pt-p2
            xD1 = pt.getX() - start.getX();
            xD2 = pt.getX() - end.getX();
            yD1 = pt.getY() - start.getY();
            yD2 = pt.getY() - end.getY();
            segmentLen1 = (Math.sqrt(xD1 * xD1 + yD1 * yD1) + Math.sqrt(xD2 * xD2 + yD2 * yD2));


            // calculate the combined length of the two segments
            // between Pt-p3 and Pt-p4
            xD1 = pt.getX() - line.getStart().getX();
            xD2 = pt.getX() - line.getEnd().getX();
            yD1 = pt.getY() - line.getStart().getY();
            yD2 = pt.getY() - line.getEnd().getY();
            segmentLen2 = (Math.sqrt(xD1 * xD1 + yD1 * yD1) + Math.sqrt(xD2 * xD2 + yD2 * yD2));

            // if the lengths of both sets of segments are the same as
            // the lenghts of the two lines the point is actually
            // on the line segment.

            // if the point isn't on the line, return null
            if (Math.abs(len1 - segmentLen1) > 0.01 || Math.abs(len2 - segmentLen2) > 0.01)
                return null;
        }
        // return the valid intersection
        if (Double.isNaN(pt.getX()) || Double.isNaN(pt.getY())) {
            throw new ArithmeticException("Intersection of " + this + " and " + line + " is " + pt + ". Angle between lines is "+df+". Check Coordinate.MAX_DELTA");
        }
        return pt;
    }

    public boolean isOnLine(Coordinate coordinate) {
        return isOnLine(coordinate, true);
    }

    public boolean isOnLine(Coordinate coordinate, boolean checkBounds) {
        if (coordinate == null) {
            throw new IllegalArgumentException(new NullPointerException("'coordinate' can't be null."));
        }
        double y = calculateY(coordinate.getX());
        if (Math.abs(y - coordinate.getY()) > Coordinate.MAX_DELTA) {
            return false;
        }
        if (checkBounds) {
            if ((getStart().getY() <= y) && (getEnd().getY() >= y)) {
                return true;
            } else if ((getStart().getY() >= y) && (getEnd().getY() <= y)) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }


    public double calculateY(double x) {
        return (x - getStart().getX()) * (getEnd().getY() - getStart().getY()) / (getEnd().getX() - getStart().getX()) + getStart().getY();

    }


    public double calculateX(double y) {
        return (y - getStart().getY()) * (getEnd().getX() - getStart().getX()) / (getEnd().getY() - getStart().getY()) + getStart().getX();
    }


    public double getDistance(Coordinate coordinate) {
        if (coordinate == null) {
            throw new IllegalArgumentException(new NullPointerException("'coordinate' can't be null."));
        }
        double dx = Math.abs(coordinate.getX() - calculateX(coordinate.getY()));
        double dy = Math.abs(coordinate.getY() - calculateY(coordinate.getX()));
        if (Double.isNaN(dx) || Double.isInfinite(dx)) {
            return dy;
        } else if (Double.isNaN(dy) || Double.isInfinite(dy)) {
            return dx;
        } else if ((dx == 0) && (dy == 0)) {
            return 0;
        }
        return ((dx * dy) / Math.sqrt(dx * dx + dy * dy));
    }

    public double getDistance(MathLine line) {
        if (line == null) {
            throw new IllegalArgumentException(new NullPointerException("'line' can't be null."));
        }
        if (!isParallel(line)) {
            throw new IllegalArgumentException("Lines isn't parallel.");
        }

        return getDistance(line.getStart());
    }

    public Coordinate getParallelLineIntersection(final MathLine line) {
        return getParallelLineIntersection(line, Coordinate.MAX_DELTA);

    }

    public Coordinate getParallelLineIntersection(final MathLine line, double delta) {
        if (line == null) {
            throw new IllegalArgumentException(new NullPointerException("'line' can't be null."));
        }

        if (getDistance(line) > delta) {
            return null;
        }
        ArrayList<Double> arX = new ArrayList<Double>() {{
            add(getStart().getX());
            add(getEnd().getX());
            add(line.getStart().getX());
            add(line.getEnd().getX());
        }};
        ArrayList<Double> arY = new ArrayList<Double>() {{
            add(getStart().getY());
            add(getEnd().getY());
            add(line.getStart().getY());
            add(line.getEnd().getY());
        }};
        Collections.sort(arX);
        Collections.sort(arY);
        return new Coordinate((arX.get(1) + arX.get(2)) / 2, (arY.get(1) + arY.get(2)) / 2);

    }

    public double getLength() {
        double dx = getEnd().getX() - getStart().getX();
        double dy = getEnd().getY() - getStart().getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public void setStart(Coordinate start) {
        if (start == null) {
            throw new IllegalArgumentException(new NullPointerException("'start' can't be null."));
        }
        this.start = start;
    }

    public void setEnd(Coordinate end) {
        if (end == null) {
            throw new IllegalArgumentException(new NullPointerException("'end' can't be null."));
        }
        this.end = end;
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MathLine)) return false;

        MathLine mathLine = (MathLine) o;

        if (end != null ? !end.equals(mathLine.end) : mathLine.end != null) return false;
        if (start != null ? !start.equals(mathLine.start) : mathLine.start != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = start != null ? start.hashCode() : 0;
        result = 31 * result + (end != null ? end.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MathLine{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}