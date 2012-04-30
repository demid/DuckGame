package com.editor.visualcomponent;

import com.editor.res.Properties;
import com.game.util.Coordinate;
import com.game.util.MathPolygon;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Date: 26.04.12
 * Time: 14:23
 *
 * @author: Alexey
 */
public class JCrossWay extends JComponent {
    private final static Logger LOGGER = Logger.getLogger(JCrossWay.class);
    private double angle;
    private double scale;


    private RoadEntry[] roads;

    private int connectorSize = Properties.getInt(Properties.Settings.WAY_CONNECTOR_SIZE);
    private int selectionPolygonWidth = Properties.getInt(Properties.Settings.SELECTION_POLYGON_WIDTH);
    private double maxWays;
    private double size;
    private double triangleAngle;
    private double catet;

    //==================================

    private boolean selected = false;
    private MathPolygon selectionPolygon;

    //==================================

    public JCrossWay(int maxPlaces, double angle, double scale) {
        if (maxPlaces < 1) {
            throw new IllegalArgumentException("'maxPlaces'  should be great than 0.");
        }
        roads = new RoadEntry[maxPlaces];
        this.angle = angle;
        this.scale = scale;

        if (maxPlaces > 1) {
            triangleAngle = 2 * Math.PI / maxPlaces;
        } else {
            triangleAngle = Math.PI;
        }
        updateState(true);
        setOpaque(true);
        addMouseMotionListener(mouseAdapter);
        addMouseListener(mouseAdapter);
    }

    public void setAngle(double angle) {
        this.angle = angle;
        updateState(false);
    }

    public void setScale(double scale) {
        this.scale = scale;
        updateState(true);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getPlacesCount() {
        return roads.length;
    }

    public List<JRoad> getRoads() {
        ArrayList<JRoad> list = new ArrayList<JRoad>();
        for (RoadEntry roadEntry : roads) {
            if (roadEntry != null) {
                list.add(roadEntry.getRoad());
            }
        }
        return Collections.unmodifiableList(list);
    }

    public JCrossWay attachRoad(int place, JRoad road, boolean toStart) {
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

        roads[place] = new RoadEntry(road, toStart);
        updateState(true);
        return this;
    }

    public JCrossWay detachRoad(int place) {
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
        updateState(true);
        return this;
    }


    public JRoad getRoad(int place) {
        if (place >= roads.length) {
            throw new IllegalArgumentException("'place'  number can't be great that available place count.");
        }
        RoadEntry roadEntry = roads[place];
        if (roadEntry == null) {
            return null;
        }
        return roadEntry.getRoad();
    }


    private void drawConnectionPlace(Graphics2D g, int pNumber, double startX, double startY, double endX, double endY) {
        Color c = g.getColor();
        g.setColor(Color.LIGHT_GRAY);
        JRoad road = getRoad(pNumber);
        double waysNumber = 1;
        if (road != null) {
            waysNumber = road.getWaysNumber();
        }
        double dx = (endX - startX) / maxWays;
        double dy = (endY - startY) / maxWays;
        int offs = (int) ((maxWays - waysNumber) / 2);
        for (int i = offs; (i - offs) < waysNumber; i++) {
            g.fill3DRect((int) (startX + (i * dx) + (dx / 2) - connectorSize / 2), (int) (startY + (i * dy) + (dy / 2) - connectorSize / 2), connectorSize, connectorSize, true);
        }
        g.setColor(c);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        //TODO add to properties file
        Stroke baseStroke = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        Stroke selectedStroke = new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);


        //g.drawRect(0,0,(int)size,(int)size);
        for (int i = 0; i < getPlacesCount(); i++) {
            double startX = (catet * scale * Math.cos(triangleAngle * i + triangleAngle / 2)) + size / 2;
            double startY = (catet * scale * Math.sin(triangleAngle * i + triangleAngle / 2)) + size / 2;
            double endX = (catet * scale * Math.cos(triangleAngle * (i + 1) + triangleAngle / 2)) + size / 2;
            double endY = (catet * scale * Math.sin(triangleAngle * (i + 1) + triangleAngle / 2)) + size / 2;
            if (selected) {
                ((Graphics2D) g).setStroke(selectedStroke);
            } else {
                ((Graphics2D) g).setStroke(baseStroke);
            }
            g.drawLine((int) startX, (int) startY, (int) endX, (int) endY);
            ((Graphics2D) g).setStroke(baseStroke);
            drawConnectionPlace((Graphics2D) g, i, startX, startY, endX, endY);
        }
    }


    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, (int) size, (int) size);
        updateState(false);
    }

    public void setPosition(int x, int y) {
        super.setBounds(x, y, (int) size, (int) size);
        updateState(false);
    }


    void updateState(boolean updatePosition) {

        maxWays = 1;
        List<JRoad> roadList = getRoads();
        for (JRoad road : roadList) {
            if (road.getWaysNumber() > maxWays) {
                maxWays = road.getWaysNumber();
            }
        }
        if (getPlacesCount() > 1) {
            catet = (maxWays / 2) / (Math.sin(triangleAngle / 2));
        } else {
            catet = maxWays / 2;
        }
        size = catet * 2 * scale;

        Coordinate[] coordinates = new Coordinate[getPlacesCount()];
        for (int i = 0; i < getPlacesCount(); i++) {
            double startX = (catet * scale * Math.cos(triangleAngle * i + triangleAngle / 2)) + size / 2;
            double startY = (catet * scale * Math.sin(triangleAngle * i + triangleAngle / 2)) + size / 2;
            double endX = (catet * scale * Math.cos(triangleAngle * (i + 1) + triangleAngle / 2)) + size / 2;
            double endY = (catet * scale * Math.sin(triangleAngle * (i + 1) + triangleAngle / 2)) + size / 2;
            coordinates[i] = new Coordinate(startX, startY);
            JRoad road = getRoad(i);
            if (road != null) {
                double waysNumber = road.getWaysNumber();
                List<JRoad.WayEntry> wayEntries = road.getWaysList();

                double dx = (endX - startX) / maxWays;
                double dy = (endY - startY) / maxWays;
                int offs = (int) ((maxWays - waysNumber) / 2);
                for (int j = offs; (j - offs) < wayEntries.size(); j++) {
                    int sX = (int) (startX + (j * dx) + (dx / 2));
                    int sY = (int) (startY + (j * dy) + (dy / 2));
                    int x = getX();
                    int y = getY();

                    Point point = new Point(sX + x, sY + y);
                    if (beginOfRoadIn(i)) {
                        wayEntries.get(j - offs).setStart(point);
                    } else {
                        wayEntries.get(j - offs).setEnd(point);
                    }
                }

                road.update(wayEntries);

            }
        }
        selectionPolygon = new MathPolygon(coordinates);
        if (updatePosition) {
            setBounds(getX(), getY(), (int) size, (int) size);
        }
    }

    public boolean beginOfRoadIn(int place) {
        if (place >= roads.length) {
            throw new IllegalArgumentException("'place'  number can't be great that available place count.");
        }
        RoadEntry roadEntry = roads[place];
        if (roadEntry == null) {
            throw new IllegalStateException("There is no road in this place.");
        }
        return roadEntry.isMyBegin();
    }

    MouseAdapter mouseAdapter = new MouseAdapter() {
        private Point point = null;
        private boolean onLine;

        @Override
        public void mousePressed(MouseEvent e) {
            onLine = selectionPolygon.isNear(new Coordinate(e.getX(), e.getY()), selectionPolygonWidth);
            point = e.getPoint();
            LOGGER.trace("mousePressed : " + e.getPoint());

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            LOGGER.trace("mouseReleased : " + e.getPoint());
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (selectionPolygon.isNear(new Coordinate(e.getX(), e.getY()), selectionPolygonWidth)) {
                selected = !selected;
                LOGGER.trace("Selected : " + selected);
                getParent().repaint();
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (onLine) {
                setPosition((int) (getX() + e.getX() - point.getX()), (int) (getY() + e.getY() - point.getY()));
            }
            LOGGER.trace("mouseDragged : " + e.getPoint());
        }
    };

    private static class RoadEntry {
        private JRoad road;
        private boolean myBegin;

        private RoadEntry(JRoad road, boolean myBegin) {
            if (road == null) {
                throw new IllegalArgumentException(new NullPointerException("'road' can't be null."));
            }

            this.road = road;
            this.myBegin = myBegin;
        }

        public JRoad getRoad() {
            return road;
        }

        public void setRoad(JRoad road) {
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
