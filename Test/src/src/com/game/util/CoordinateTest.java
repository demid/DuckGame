package com.game.util;

import junit.framework.TestCase;

/**
 * Date: 22.04.12
 * Time: 15:27
 *
 * @author: Alexey
 */
public class CoordinateTest extends TestCase {
    public void testRotate() throws Exception {
        Coordinate coordinate;
        coordinate = new Coordinate(10, 0);
        assertEquals(new Coordinate(10, 0), coordinate.rotate((2*Math.PI )));

        coordinate = new Coordinate(10, 0);
        assertEquals(new Coordinate(-10, 0), coordinate.rotate( (Math.PI )));

        coordinate = new Coordinate(10, 0);
        assertEquals(new Coordinate(0, 10), coordinate.rotate( (Math.PI/2 )));

        coordinate = new Coordinate(10, 0);
        assertEquals(new Coordinate(0, -10), coordinate.rotate( (3*Math.PI/2 )));


    }

    public void testRotateAndMove() throws Exception {

    }
}
