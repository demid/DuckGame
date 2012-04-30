package com.game.util;

import junit.framework.TestCase;

/**
 * Date: 30.04.12
 * Time: 10:48
 *
 * @author: Alexey
 */
public class MathPolygonTest extends TestCase {
    public void testInPolygon() throws Exception {
        MathPolygon mathPolygon;
        mathPolygon = new MathPolygon(new Coordinate(-10,-10),new Coordinate(-10,10),new Coordinate(10,10),new Coordinate(10,-10));

        assertTrue(mathPolygon.inPolygon(new Coordinate(9.9, 9.9)));
        assertFalse(mathPolygon.inPolygon(new Coordinate(-11, -11)));
        assertTrue(mathPolygon.inPolygon(new Coordinate(-10,-10)));
        assertTrue(mathPolygon.inPolygon(new Coordinate(-10,0)));
        assertFalse(mathPolygon.inPolygon(new Coordinate(-11,0)));
        assertTrue(mathPolygon.inPolygon(new Coordinate(0, 0)));

        mathPolygon = new MathPolygon(new Coordinate(-10,10),new Coordinate(-10,0),new Coordinate(10,0),new Coordinate(10,10),new Coordinate(0,5));
        assertTrue(mathPolygon.inPolygon(new Coordinate(-7,7)));
        assertFalse(mathPolygon.inPolygon(new Coordinate(0,7)));
        assertTrue(mathPolygon.inPolygon(new Coordinate(0,5)));



    }

    public void testIsNear(){
        MathPolygon mathPolygon;
        mathPolygon = new MathPolygon(new Coordinate(-10,-10),new Coordinate(-10,10),new Coordinate(10,10),new Coordinate(10,-10));
        assertTrue(mathPolygon.isNear(new Coordinate(-10,-10),1));
        assertFalse(mathPolygon.isNear(new Coordinate(0,0),1));
        assertTrue(mathPolygon.isNear(new Coordinate(0,0),10));
        assertFalse(mathPolygon.isNear(new Coordinate(-9,-9),0.9));
        assertTrue(mathPolygon.isNear(new Coordinate(-9,-9),1));
    }
}
