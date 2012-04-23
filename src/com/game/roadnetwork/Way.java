package com.game.roadnetwork;

/**
 * @author: Alexey
 */
public class Way {

    private Direction allowedDirection;
    private Road road;

    public Direction getAllowedDirection() {
        return allowedDirection;
    }

    public void setAllowedDirection(Direction allowedDirection) {
        if (allowedDirection == null) {
            throw new IllegalArgumentException(new NullPointerException("'allowedDirection' can't be null."));
        }
        this.allowedDirection = allowedDirection;
    }

    public Way(Direction allowedDirection) {
        if (allowedDirection == null) {
            throw new IllegalArgumentException(new NullPointerException("'allowedDirection' can't be null."));
        }
        this.allowedDirection = allowedDirection;
    }

    /**
     * Create way with list of allowed directions.
     *
     * @param direction list of direction.
     */

    public boolean isAllowed(Direction direction) {
        return allowedDirection == direction;
    }


    public Road getRoad() {
        return road;
    }

    /**
     * This method is called automatically when road is being add to {@link Road}.
     *
     * @param road
     */
    void setRoad(Road road) {
        this.road = road;
    }
}
