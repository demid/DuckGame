package com.game.util;

import com.game.roadnetwork.Road;
import com.game.roadnetwork.Way;
import org.jgrapht.graph.DefaultEdge;

/**
 * Date: 19.04.12
 * Time: 15:02
 *
 * @author: Alexey
 */
public class WayEdge extends DefaultEdge {
    private Way way;

    public WayEdge() {
    }

    public WayEdge(Way way) {
        this.way = way;
    }

    public Way getWay() {
        return way;
    }


}
