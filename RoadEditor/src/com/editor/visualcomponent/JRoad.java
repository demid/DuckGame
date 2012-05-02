package com.editor.visualcomponent;

import com.editor.res.Properties;
import com.editor.screen.ComponentContainer;
import com.editor.screen.WorkComponent;
import com.game.roadnetwork.Way;
import com.game.util.Coordinate;
import com.game.util.MathLine;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Date: 25.04.12
 * Time: 12:09
 *
 * @author: Alexey
 */
public class JRoad extends JComponent implements WorkComponent {
    private final static Logger LOGGER = Logger.getLogger(JRoad.class);
    private JCrossWay start;
    private JCrossWay end;
    private ComponentContainer componentContainer;
    private boolean selected = false;
    private java.util.List<WayEntry> waysList = new ArrayList();
    private int selectionPolygonWidth = Properties.getInt(Properties.Settings.SELECTION_POLYGON_WIDTH);

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
        setOpaque(false);
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
        if ((maxX != Integer.MIN_VALUE) && (minX != Integer.MAX_VALUE) && (maxY != Integer.MIN_VALUE) && (minY != Integer.MAX_VALUE)) {
            setBounds(minX, minY, maxX - minX, maxY - minY);
        } else {
            setBounds(0, 0, 0, 0);
        }
        Collections.copy(waysList, wayEntries);
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        //TODO Change design and add parameters to configure file
        if (selected) {
            ((Graphics2D) g).setStroke(new BasicStroke(4));
        } else {
            ((Graphics2D) g).setStroke(new BasicStroke(2));
        }
        for (WayEntry wayEntry : waysList) {
            if ((wayEntry.getStart() != null) && (wayEntry.getEnd() != null)) {
                g.drawLine(wayEntry.getStart().x - getX(), wayEntry.getStart().y - getY(), wayEntry.getEnd().x - getX(), wayEntry.getEnd().y - getY());
            }
        }
    }

    public ComponentContainer getComponentContainer() {
        return componentContainer;
    }

    private boolean onLine = false;

    @Override
    public void setComponentContainer(ComponentContainer componentContainer) {
        this.componentContainer = componentContainer;
    }

    @Override
    public JComponent getComponent() {
        return this;
    }

    @Override
    public boolean doubleClicked(MouseEvent e) {
        return false;
    }

    @Override
    public boolean mouseClicked(MouseEvent e) {
        if (onLine) {
            selected = !selected;
            ComponentContainer container = getComponentContainer();
            if (container != null) {
                if (selected) {
                    container.addToSelected(this);
                } else {
                    container.removeFromSelected(this);
                }
            }
            repaint();
            return true;
        }
        return false;
    }

    @Override
    public boolean mousePressed(MouseEvent e) {
        onLine = false;
        for (WayEntry wayEntry : waysList) {
            MathLine mathLine = wayEntry.getMathLine();
            if (mathLine != null) {
                double  distance =  mathLine.getDistance(new Coordinate(e.getX()+getX(), e.getY()+getY()));
                if (distance <= selectionPolygonWidth) {
                    onLine = true;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(MouseEvent e) {
        return false;
    }

    @Override
    public boolean mouseEntered(MouseEvent e) {
        return false;
    }

    @Override
    public boolean mouseExited(MouseEvent e) {
        return false;
    }

    @Override
    public boolean mouseDragged(MouseEvent e) {
        return false;
    }

    @Override
    public boolean mouseMoved(MouseEvent e) {
        return false;
    }

    class WayEntry {
        private Point start;
        private Point end;
        private Way way;
        private MathLine mathLine;

        private void createMathLine() {
            if ((start != null) && (end != null)) {
                mathLine = new MathLine(new Coordinate(start.x, start.y), new Coordinate(end.x, end.y));
            } else {
                mathLine = null;
            }
        }

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
            createMathLine();
        }


        public Point getStart() {
            return start;
        }

        public void setStart(Point start) {
            this.start = start;
            createMathLine();
        }

        public Point getEnd() {
            return end;
        }

        public void setEnd(Point end) {
            this.end = end;
            createMathLine();
        }

        public MathLine getMathLine() {
            return mathLine;
        }
    }
}
