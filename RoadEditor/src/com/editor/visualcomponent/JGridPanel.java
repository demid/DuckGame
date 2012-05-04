package com.editor.visualcomponent;

import com.editor.screen.ComponentContainer;
import com.editor.screen.CrossWayContainer;
import com.editor.screen.WorkComponent;
import com.game.roadnetwork.Direction;
import com.game.roadnetwork.Way;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Date: 24.04.12
 * Time: 10:36
 *
 * @author: Alexey
 */
public class JGridPanel extends JPanel implements ComponentContainer, CrossWayContainer {
    private final static Logger LOGGER = Logger.getLogger(JGridPanel.class);
    //TODO add to config file
    public static final int DEFAULT_CELL_SIZE = 5;
    public static final int DEFAULT_BORDER_CELL_WIDTH = 1;
    public static final Color DEFAULT_BORDER_CELL_COLOR = Color.GRAY;


    private int cellSize = DEFAULT_CELL_SIZE;
    private int borderCellWidth = DEFAULT_BORDER_CELL_WIDTH;
    private Color gridColor = DEFAULT_BORDER_CELL_COLOR;
    private boolean drawGrid = true;
    private LinkedList<WorkComponent> workComponents = new LinkedList<WorkComponent>();
    private JGlassComponent jGlassComponent = new JGlassComponent(workComponents);

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        jGlassComponent.setBounds(0, 0, width, height);
    }

    @Override
    protected void addImpl(Component comp, Object constraints, int index) {
        super.addImpl(comp, constraints, index);
        if (comp instanceof WorkComponent) {
            ((WorkComponent) comp).setComponentContainer(this);
            workComponents.add((WorkComponent) comp);
        } else {
            LOGGER.warn(comp.getClass().getName() + " doesn't implement WorkComponent. Mouse events isn't available for it. ");
        }
    }

    public JGridPanel() {
        add(jGlassComponent);
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        if (cellSize <= 0) {
            throw new IllegalArgumentException("'cellSize' should be great than 0.");
        }
        this.cellSize = cellSize;
    }

    public int getBorderCellWidth() {
        return borderCellWidth;
    }

    public void setBorderCellWidth(int borderCellWidth) {
        this.borderCellWidth = borderCellWidth;
    }

    public Color getGridColor() {
        return gridColor;
    }

    public void setGridColor(Color gridColor) {
        this.gridColor = gridColor;
    }

    public boolean isDrawGrid() {
        return drawGrid;
    }

    public void setDrawGrid(boolean drawGrid) {
        this.drawGrid = drawGrid;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (drawGrid) {
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setColor(gridColor);
            graphics2D.setStroke(new BasicStroke(borderCellWidth));
            for (int i = 0; i <= getHeight() / cellSize; i++) {
                graphics2D.drawLine(0, i * cellSize, getWidth(), i * cellSize);
            }
            for (int i = 0; i <= getWidth() / cellSize; i++) {
                graphics2D.drawLine(i * cellSize, 0, i * cellSize, getHeight());
            }
        }
    }

    @Override
    public void addToSelected(JComponent jComponent) {
        LOGGER.trace("Selected " + jComponent);
    }

    @Override
    public void removeFromSelected(JComponent jComponent) {
        LOGGER.trace("Unselected " + jComponent);
    }


    private ActiveWayEntry first;

    @Override
    public void crossWayActivated(JCrossWay jCrossWay, int place) {
        synchronized (this) {
            ActiveWayEntry second = new ActiveWayEntry(jCrossWay, place);
            if ((first == null) || first.equals(second)) {
                first = second;
                first.getjCrossWay().setActiveConnector(place);
            } else {
                JRoad jRoad = new JRoad(new Way(Direction.FORWARD));
                add(jRoad);
                first.getjCrossWay().attachRoad(first.getPlace(), jRoad, true);
                second.getjCrossWay().attachRoad(second.getPlace(), jRoad, false);
                first.getjCrossWay().setActiveConnector(-1);
                first = null;
            }
        }

        LOGGER.trace("Activated " + jCrossWay + " " + place);
    }

    private class ActiveWayEntry {
        private JCrossWay jCrossWay;
        private int place;


        private ActiveWayEntry(JCrossWay jCrossWay) {
            this.jCrossWay = jCrossWay;
        }

        private ActiveWayEntry(JCrossWay jCrossWay, int place) {
            this.jCrossWay = jCrossWay;
            this.place = place;
        }

        public JCrossWay getjCrossWay() {
            return jCrossWay;
        }

        public int getPlace() {
            return place;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof ActiveWayEntry) {
                return jCrossWay.equals(((ActiveWayEntry) o).getjCrossWay());
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return jCrossWay.hashCode();
        }

        @Override
        public String toString() {
            return "ActiveWayEntry{" +
                    "jCrossWay=" + jCrossWay +
                    '}';
        }
    }


    private class JGlassComponent extends JComponent implements MouseListener, MouseMotionListener {
        private LinkedList<WorkComponent> workComponents;

        private JGlassComponent(LinkedList<WorkComponent> workComponents) {
            this.workComponents = workComponents;
            setOpaque(false);
            addMouseListener(this);
            addMouseMotionListener(this);
            if (this.workComponents == null) {
                this.workComponents = new LinkedList<WorkComponent>();
            }
        }


        @Override
        public void mouseClicked(MouseEvent e) {
            Iterator<WorkComponent> iterator = workComponents.iterator();
            while (iterator.hasNext()) {
                WorkComponent workComponent = iterator.next();
                JComponent jComponent = workComponent.getComponent();
                if (jComponent != null) {
                    if (jComponent.getBounds().contains(e.getPoint())) {
                        MouseEvent event = SwingUtilities.convertMouseEvent(this, e, jComponent);
                        if (e.getClickCount() == 1) {
                            if (workComponent.mouseClicked(event)) {
                                if (!workComponents.getFirst().equals(workComponent)) {
                                    iterator.remove();
                                    workComponents.addFirst(workComponent);
                                }
                                return;
                            }
                        } else if (e.getClickCount() == 2) {
                            if (workComponent.doubleClicked(event)) {
                                if (!workComponents.getFirst().equals(workComponent)) {
                                    iterator.remove();
                                    workComponents.addFirst(workComponent);
                                }
                                return;
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            Iterator<WorkComponent> iterator = workComponents.iterator();
            while (iterator.hasNext()) {
                WorkComponent workComponent = iterator.next();
                JComponent jComponent = workComponent.getComponent();
                if (jComponent != null) {
                    if (jComponent.getBounds().contains(e.getPoint())) {
                        MouseEvent event = SwingUtilities.convertMouseEvent(this, e, jComponent);
                        if (workComponent.mousePressed(event)) {
                            if (!workComponents.getFirst().equals(workComponent)) {
                                iterator.remove();
                                workComponents.addFirst(workComponent);
                            }
                            return;
                        }
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            Iterator<WorkComponent> iterator = workComponents.iterator();
            while (iterator.hasNext()) {
                WorkComponent workComponent = iterator.next();
                JComponent jComponent = workComponent.getComponent();
                if (jComponent != null) {
                    if (jComponent.getBounds().contains(e.getPoint())) {
                        MouseEvent event = SwingUtilities.convertMouseEvent(this, e, jComponent);
                        if (workComponent.mouseReleased(event)) {
                            if (!workComponents.getFirst().equals(workComponent)) {
                                iterator.remove();
                                workComponents.addFirst(workComponent);
                            }
                            return;
                        }
                    }
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            Iterator<WorkComponent> iterator = workComponents.iterator();
            while (iterator.hasNext()) {
                WorkComponent workComponent = iterator.next();
                JComponent jComponent = workComponent.getComponent();
                if (jComponent != null) {
                    if (jComponent.getBounds().contains(e.getPoint())) {
                        MouseEvent event = SwingUtilities.convertMouseEvent(this, e, jComponent);
                        if (workComponent.mouseEntered(event)) {
                            if (!workComponents.getFirst().equals(workComponent)) {
                                iterator.remove();
                                workComponents.addFirst(workComponent);
                            }
                            return;
                        }
                    }
                }
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            Iterator<WorkComponent> iterator = workComponents.iterator();
            while (iterator.hasNext()) {
                WorkComponent workComponent = iterator.next();
                JComponent jComponent = workComponent.getComponent();
                if (jComponent != null) {
                    if (jComponent.getBounds().contains(e.getPoint())) {
                        MouseEvent event = SwingUtilities.convertMouseEvent(this, e, jComponent);
                        if (workComponent.mouseExited(event)) {
                            if (!workComponents.getFirst().equals(workComponent)) {
                                iterator.remove();
                                workComponents.addFirst(workComponent);
                            }
                            return;
                        }
                    }
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Iterator<WorkComponent> iterator = workComponents.iterator();
            while (iterator.hasNext()) {
                WorkComponent workComponent = iterator.next();
                JComponent jComponent = workComponent.getComponent();
                if (jComponent != null) {
                    if (jComponent.getBounds().contains(e.getPoint())) {
                        MouseEvent event = SwingUtilities.convertMouseEvent(this, e, jComponent);
                        if (workComponent.mouseDragged(event)) {
                            if (!workComponents.getFirst().equals(workComponent)) {
                                iterator.remove();
                                workComponents.addFirst(workComponent);
                            }
                            return;
                        }
                    }
                }
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            Iterator<WorkComponent> iterator = workComponents.iterator();
            while (iterator.hasNext()) {
                WorkComponent workComponent = iterator.next();
                JComponent jComponent = workComponent.getComponent();
                if (jComponent != null) {
                    if (jComponent.getBounds().contains(e.getPoint())) {
                        MouseEvent event = SwingUtilities.convertMouseEvent(this, e, jComponent);
                        if (workComponent.mouseMoved(event)) {
                            if (!workComponents.getFirst().equals(workComponent)) {
                                iterator.remove();
                                workComponents.addFirst(workComponent);
                            }
                            return;
                        }
                    }
                }
            }
        }
    }
}
