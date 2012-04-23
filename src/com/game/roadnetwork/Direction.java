package com.game.roadnetwork;

/**
 * Enum of directions.
 *
 * @author: Alexey
 */
public enum Direction {
    FORWARD, BACK;

    @Override
    public String toString() {
        switch (this) {
            case FORWARD:
                return "FORWARD";
            case BACK:
                return "BACK";
            default:
                return null;
        }

    }
}
