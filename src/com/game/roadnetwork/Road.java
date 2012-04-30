package com.game.roadnetwork;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent road as list of ways.
 *
 * @author: Alexey
 */
public class Road {

    private CrossWay start;
    private CrossWay end;

    private List<Way> waysList = new ArrayList();


    /**
     * Create empty road.
     */
    public Road() {
    }


    public Road(Way... ways) {
        for (Way way : ways) {
            if (way == null) {
                throw new IllegalArgumentException(new NullPointerException("'way' can't be null."));
            }
            if (way.getRoad() != null) {
                throw new IllegalStateException("This way is already in road.");
            }
            way.setRoad(this);
            waysList.add(way);
        }
    }

    public Way getWay(int wayIndex) {
        if (wayIndex >= waysList.size()) {
            throw new IllegalArgumentException("'wayIndex' can't be great that available ways count.");
        }

        return waysList.get(wayIndex);
    }

    public CrossWay getStart() {
        return start;
    }

    public int getWaysNumber() {
        return waysList.size();
    }

    /**
     * This method is called automatically when road is being connect to {@link CrossWay}.
     *
     * @param start
     */
    void setStart(CrossWay start) {
        this.start = start;
    }

    public CrossWay getEnd() {
        return end;
    }

    /**
     * This method is called automatically when road is being connect to {@link CrossWay}.
     *
     * @param end
     */
    void setEnd(CrossWay end) {
        this.end = end;
    }


}
