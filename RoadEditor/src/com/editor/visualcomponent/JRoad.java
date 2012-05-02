package com.editor.visualcomponent;

import com.game.roadnetwork.Way;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Date: 25.04.12
 * Time: 12:09
 *
 * @author: Alexey
 */
public class JRoad extends JComponent {
    private JCrossWay start;
    private JCrossWay end;

    private java.util.List<WayEntry> waysList = new ArrayList();

    public JRoad(Way... ways) {
        for (Way way : ways) {
            if (way == null) {
                throw new IllegalArgumentException(new NullPointerException("'way' can't be null."));
            }
            if (way.getRoad() != null) {
                throw new IllegalStateException("This way is already in road.");
            }
            waysList.add(new WayEntry(way));
        }
        setOpaque(true);
    }

    public JCrossWay getStart() {
        return start;
    }

    void setStart(JCrossWay start) {
        this.start = start;
    }

    public JCrossWay getEnd() {
        return end;
    }

    void setEnd(JCrossWay end) {
        this.end = end;
    }

    public int getWaysNumber() {
        return waysList.size();
    }

    List<WayEntry> getWaysList() {
        return Collections.unmodifiableList(waysList);
    }


    void update(List<WayEntry> wayEntries) {
        int maxX = Integer.MIN_VALUE,
                minX = Integer.MAX_VALUE,
                maxY = Integer.MIN_VALUE,
                minY = Integer.MAX_VALUE;
        if (wayEntries.size() != waysList.size()) {
            throw new IllegalArgumentException("Current ways list and new ways list can't be different size.");
        }
        for (WayEntry wayEntry : wayEntries) {
            if (wayEntry.getStart() != null) {
                if (wayEntry.getStart().getX() > maxX) {
                    maxX = (int) wayEntry.getStart().getX();
                }
                if (wayEntry.getStart().getX() < minX) {
                    minX = (int) wayEntry.getStart().getX();
                }
                if (wayEntry.getStart().getY() > maxY) {
                    maxY = (int) wayEntry.getStart().getY();
                }
                if (wayEntry.getStart().getY() < minY) {
                    minY = (int) wayEntry.getStart().getY();
                }

            }
            if (wayEntry.getEnd() != null) {
                if (wayEntry.getEnd().getX() > maxX) {
                    maxX = (int) wayEntry.getEnd().getX();
                }
                if (wayEntry.getEnd().getX() < minX) {
                    minX = (int) wayEntry.getEnd().getX();
                }
                if (wayEntry.getEnd().getY() > maxY) {
                    maxY = (int) wayEntry.getEnd().getY();
                }
                if (wayEntry.getEnd().getY() < minY) {
                    minY = (int) wayEntry.getEnd().getY();
                }
            }
        }
        if((maxX != Integer.MIN_VALUE)&&(minX != Integer.MAX_VALUE)&&(maxY !=Integer.MIN_VALUE)&&(minY != Integer.MAX_VALUE)){
            setBounds(minX, minY, maxX - minX, maxY - minY);
        }else{
            setBounds(0,0,0,0);
        }
        Collections.copy(waysList, wayEntries);
    }


    @Override
    protected void paintComponent(Graphics g) {
        //TODO Change design
        ((Graphics2D)g).setStroke(new BasicStroke(2));
        for (WayEntry wayEntry : waysList) {
            if ((wayEntry.getStart() != null) && (wayEntry.getEnd() != null)) {
                g.drawLine(wayEntry.getStart().x - getX(), wayEntry.getStart().y - getY(), wayEntry.getEnd().x- getX(), wayEntry.getEnd().y - getY());
            }
        }
    }

    class WayEntry {
        private Point start;
        private Point end;
        private Way way;

        public WayEntry(Way way) {
            this(null, null, way);
        }

        private WayEntry(Point start, Point end, Way way) {
            if (way == null) {
                throw new IllegalArgumentException(new NullPointerException("'way' can't be null."));
            }
            this.start = start;
            this.end = end;
            this.way = way;
        }


        public Point getStart() {
            return start;
        }

        public void setStart(Point start) {
            this.start = start;
        }

        public Point getEnd() {
            return end;
        }

        public void setEnd(Point end) {
            this.end = end;
        }
    }
}
