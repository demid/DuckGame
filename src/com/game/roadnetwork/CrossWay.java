package com.game.roadnetwork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Cross way  it is set of {@link com.game.roadnetwork.Way} that connected in special places.
 * Recommended shema of places for connection of roads :
 * (0)
 * (7) (1)
 * (6)     (2)
 * (5)  (3)
 * (4)
 * To each place can be connected only one road.
 *
 * @author: Alexey
 */
public class CrossWay {
    private RoadEntry[] roads;
    private int roadsInCrossWay;

    public CrossWay(int maxPlaces) {
        if(maxPlaces <=0){
            throw new IllegalArgumentException("'maxPlaces'  should be great than 0.");
        }
        roads = new RoadEntry[maxPlaces];
    }

    public int getPlacesCount() {
        return roads.length;
    }

    public int getRoadsInCrossWay() {
        return roadsInCrossWay;
    }

    public List<Road> getRoads() {
        ArrayList<Road> list = new ArrayList<Road>();
        for (RoadEntry roadEntry : roads) {
            if (roadEntry != null) {
                list.add(roadEntry.getRoad());
            }
        }
        return Collections.unmodifiableList(list);
    }

    public CrossWay attachRoad(int place, Road road, boolean toStart) {
        if (place >= roads.length) {
            throw new IllegalArgumentException("Can't attach road. 'place'  number can't be great that available place count.");
        }
        if (road == null) {
            throw new IllegalArgumentException(new NullPointerException("Can't attach road. 'road' can't be null."));
        }
        if (roads[place] != null) {
            throw new IllegalStateException("Can't attach road. Some road is already connected to this place. Invoke detach first.");
        }
        if (toStart) {
            if (road.getStart() != null) {
                throw new IllegalStateException("Can't attach road. This 'road' end is already connected to some CrossWay");
            } else {
                road.setStart(this);
            }
        } else {
            if (road.getEnd() != null) {
                throw new IllegalStateException("Can't attach road. This 'road' end is already connected to some CrossWay");
            } else {
                road.setEnd(this);
            }
        }

        roadsInCrossWay++;
        roads[place] = new RoadEntry(road, toStart);
        return this;
    }

    public CrossWay detachRoad(int place) {
        if (place >= roads.length) {
            throw new IllegalArgumentException("'place'  number can't be great that available place count.");
        }
        RoadEntry roadEntries = roads[place];
        if (roadEntries == null) {
            throw new IllegalStateException("There is no road in this place.");
        }
        roads[place] = null;
        if (roadEntries.isMyBegin()) {
            roadEntries.getRoad().setStart(null);
        } else {
            roadEntries.getRoad().setEnd(null);
        }
        roadsInCrossWay--;
        return this;
    }

    public Road getRoad(int place) {
        if (place >= roads.length) {
            throw new IllegalArgumentException("'place'  number can't be great that available place count.");
        }
        RoadEntry roadEntry = roads[place];
        if (roadEntry == null) {
            return null;
        }
        return roadEntry.getRoad();
    }

    public boolean beginOfRoadIn(int place){
        if (place >= roads.length) {
            throw new IllegalArgumentException("'place'  number can't be great that available place count.");
        }
        RoadEntry roadEntry = roads[place];
        if(roadEntry == null){
            throw new IllegalStateException("There is no road in this place.");
        }
        return roadEntry.isMyBegin();
    }


    private static class RoadEntry {
        private Road road;
        private boolean myBegin;

        private RoadEntry(Road road, boolean myBegin) {
            if (road == null) {
                throw new IllegalArgumentException(new NullPointerException("'road' can't be null."));
            }

            this.road = road;
            this.myBegin = myBegin;
        }

        public Road getRoad() {
            return road;
        }

        public void setRoad(Road road) {
            this.road = road;
        }

        public boolean isMyBegin() {
            return myBegin;
        }

        public void setMyBegin(boolean myBegin) {
            this.myBegin = myBegin;
        }
    }
}
