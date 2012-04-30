package com.game.util;

import junit.framework.TestCase;

/**
 * Date: 22.04.12
 * Time: 10:52
 *
 * @author: Alexey
 */
public class MathLineTest extends TestCase {

    public void testGetLineIntersection() throws Exception {
        //TODO complete this test
    }

    public void testIsOnLine() throws Exception {
        MathLine line;
        Coordinate coordinate = new Coordinate(10,0);
        line = new MathLine(new Coordinate(10,10),new Coordinate(10,-10));
        assertTrue(line.isOnLine(coordinate));

        line = new MathLine(new Coordinate(0, 0), new Coordinate(5, 0));
        coordinate = new Coordinate(1, 0);
        assertTrue(line.isOnLine(coordinate));


        line = new MathLine(new Coordinate(0, 0), new Coordinate(5, 5));
        coordinate = new Coordinate(0, 0);
        assertTrue(line.isOnLine(coordinate));

        line = new MathLine(new Coordinate(0, 0), new Coordinate(5, 5));
        coordinate = new Coordinate(5, 5);
        assertTrue(line.isOnLine(coordinate));

        line = new MathLine(new Coordinate(0, 0), new Coordinate(5, 5));
        coordinate = new Coordinate(5, 5.1f);
        assertFalse(line.isOnLine(coordinate));


        line = new MathLine(new Coordinate(0, 0), new Coordinate(5, 5));
        coordinate = new Coordinate(2, 2);
        assertTrue(line.isOnLine(coordinate));

        line = new MathLine(new Coordinate(0, 0), new Coordinate(5, 5));
        coordinate = new Coordinate(2, 3);
        assertFalse(line.isOnLine(coordinate));

        line = new MathLine(new Coordinate(0, 0), new Coordinate(5, 5));
        coordinate = new Coordinate(2, 3);
        assertFalse(line.isOnLine(coordinate));


        line = new MathLine(new Coordinate(0, 0), new Coordinate(5, 5));
        coordinate = new Coordinate(6, 6);
        assertFalse(line.isOnLine(coordinate));

        line = new MathLine(new Coordinate(0, 0), new Coordinate(5, 5));
        coordinate = new Coordinate(6, 6);
        assertTrue(line.isOnLine(coordinate, false));


    }

    public void testIsParallel() throws Exception {
        MathLine line1 = new MathLine(new Coordinate(0, 0), new Coordinate(5, 5));
        MathLine line2 = new MathLine(new Coordinate(2, 0), new Coordinate(7, 5));
        assertTrue(line1.isParallel(line2));

        line1 = new MathLine(new Coordinate(0, 0), new Coordinate(5, 0));
        line2 = new MathLine(new Coordinate(2, 0), new Coordinate(7, 0));
        assertTrue(line1.isParallel(line2));

        line1 = new MathLine(new Coordinate(0, 0), new Coordinate(5, 0));
        line2 = new MathLine(new Coordinate(2, 0), new Coordinate(7, 1));
        assertFalse(line1.isParallel(line2));

        line1 = new MathLine(new Coordinate(0, 0), new Coordinate(5, 0));
        line2 = new MathLine(new Coordinate(2, 0), new Coordinate(7, 0.000001f));
        assertFalse(line1.isParallel(line2));

        line1 = new MathLine(new Coordinate(0, 2), new Coordinate(0, 5));
        line2 = new MathLine(new Coordinate(0, 2), new Coordinate(0, 7));
        assertTrue(line1.isParallel(line2));

        line1 = new MathLine(new Coordinate(0, 2), new Coordinate(0, 5));
        line2 = new MathLine(new Coordinate(5, 2), new Coordinate(5, 7));
        assertTrue(line1.isParallel(line2));


    }

    public void testCalculateY() throws Exception {
        MathLine line = new MathLine(new Coordinate(-5, 0), new Coordinate(5, 0));
        assertEquals(0d, line.calculateY(0));
        line = new MathLine(new Coordinate(0, 0), new Coordinate(5, 5));
        assertEquals(3d, line.calculateY(3));
        line = new MathLine(new Coordinate(-5, 0), new Coordinate(5, 0));
        assertEquals(0d, line.calculateY(-10));
        line = new MathLine(new Coordinate(0, -5), new Coordinate(0, 5));
        assertTrue(Double.isNaN(line.calculateY(0)));
    }

    public void testCalculateX() throws Exception {
        MathLine line = new MathLine(new Coordinate(-5, 0), new Coordinate(5, 0));
        assertTrue(Double.isNaN(line.calculateX(0)));
        line = new MathLine(new Coordinate(0, 0), new Coordinate(5, 5));
        assertEquals(3d, line.calculateX(3));
        line = new MathLine(new Coordinate(-5, 0), new Coordinate(5, 0));
        assertTrue(Double.isInfinite(line.calculateX(-10)));
        line = new MathLine(new Coordinate(0, -5), new Coordinate(0, 5));
        assertEquals(0d, line.calculateX(0));
    }

    public void testGetDistanceToPoint() throws Exception {
        MathLine line;
        Coordinate point;

        line = new MathLine(new Coordinate(-2, 0), new Coordinate(0, 2));
        point = new Coordinate(0, 0);
        assertEquals(1.414213562373095d, line.getDistance(point));

        line = new MathLine(new Coordinate(-5, 0), new Coordinate(5, 0));
        point = new Coordinate(0, 1);
        assertEquals(1d, line.getDistance(point));

        line = new MathLine(new Coordinate(1, -5), new Coordinate(1, 5));
        point = new Coordinate(-10, 1);
        assertEquals(11d, line.getDistance(point));


        line = new MathLine(new Coordinate(0, -5), new Coordinate(0, 5));
        point = new Coordinate(0, 0);
        assertEquals(0d, line.getDistance(point));

        line = new MathLine(new Coordinate(0, -5), new Coordinate(0, 5));
        point = new Coordinate(1, 0);
        assertEquals(1d, line.getDistance(point));

    }

    public void testGetDistanceToLine() throws Exception {
        MathLine line1 = new MathLine(new Coordinate(0, 0), new Coordinate(5, 5));
        MathLine line2 = new MathLine(new Coordinate(2, 0), new Coordinate(7, 5));
        assertEquals(1.414213562373095d, line1.getDistance(line2));

        line1 = new MathLine(new Coordinate(0, 0), new Coordinate(5, 0));
        line2 = new MathLine(new Coordinate(2, 0), new Coordinate(7, 0));
        assertEquals(0d, line1.getDistance(line2));

        try {
            line1 = new MathLine(new Coordinate(0, 0), new Coordinate(5, 0));
            line2 = new MathLine(new Coordinate(2, 0), new Coordinate(7, 1));
            assertEquals(0f, line1.getDistance(line2));
            fail("Lines aren't parallel.");
        } catch (Exception e) {
        }

        try {

            line1 = new MathLine(new Coordinate(0, 0), new Coordinate(5, 0));
            line2 = new MathLine(new Coordinate(2, 0), new Coordinate(7, 0.000001f));
            assertEquals(0f, line1.getDistance(line2));
            fail("Lines aren't parallel.");
        } catch (Exception e) {
        }

        line1 = new MathLine(new Coordinate(0, 2), new Coordinate(0, 5));
        line2 = new MathLine(new Coordinate(2, 2), new Coordinate(2, 7));
        assertEquals(2d, line1.getDistance(line2));

        line1 = new MathLine(new Coordinate(0, 2), new Coordinate(0, 5));
        line2 = new MathLine(new Coordinate(5, 2), new Coordinate(5, 7));
        assertEquals(5d, line1.getDistance(line2));

        line1 = new MathLine(new Coordinate(0, 0), new Coordinate(10, 10));
        line2 = new MathLine(new Coordinate(-10, 0), new Coordinate(0, 10));
        assertEquals(7.071067811865475d, line1.getDistance(line2));
    }

    public void testGetParallelLineIntersection() throws Exception {
        MathLine line1;
        MathLine line2;
        line1 = new MathLine(new Coordinate(-1, 0), new Coordinate(0, 1));
        line2 = new MathLine(new Coordinate(0, -1), new Coordinate(1, 0));
        assertEquals(new Coordinate(0,0), line1.getParallelLineIntersection(line2,3));

        line1 = new MathLine(new Coordinate(-1, -1), new Coordinate(1, 1));
        line2 = new MathLine(new Coordinate(0, 0), new Coordinate(2, 2));
        assertEquals(new Coordinate(0.5f,0.5f), line1.getParallelLineIntersection(line2));

    }

    public void testGetLength() throws Exception {
        //TODO complete this test
    }
}
