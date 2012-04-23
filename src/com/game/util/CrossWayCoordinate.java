package com.game.util;

import com.game.roadnetwork.CrossWay;

/**
 * Date: 21.04.12
 * Time: 10:09
 *
 * @author: Alexey
 */
public class CrossWayCoordinate extends Coordinate {
    private CrossWay crossWay;


    public CrossWayCoordinate(double x, double y, CrossWay crossWay) {
        super(x, y);
        this.crossWay = crossWay;
    }

    public CrossWayCoordinate(Coordinate coordinate, CrossWay crossWay) {
        super(coordinate.getX(), coordinate.getY());
        this.crossWay = crossWay;
    }

    public CrossWay getCrossWay() {
        return crossWay;
    }

    public void setCrossWay(CrossWay crossWay) {
        this.crossWay = crossWay;
    }

    @Override
    public boolean equals(Object o) {
        if (super.equals(o)) {
            CrossWayCoordinate that = (CrossWayCoordinate) o;
            if ((crossWay != null) && (that.getCrossWay() != null)) {
                return crossWay.equals(that.getCrossWay());
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
