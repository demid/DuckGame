package com.editor.visualcomponent;

import com.editor.screen.ComponentContainer;
import com.editor.screen.CrossWayContainer;
import com.game.roadnetwork.Direction;
import com.game.roadnetwork.Way;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    private ActiveWayEntry first;

    public JGridPanel() {
        add(jGlassComponent);
    }


    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        jGlassComponent.setBounds(0, 0, width, height);
    }

    @Override
    protected void addImpl(Component comp, Object constraints, int index) {
        super.addImpl(comp, constraints, index);
        if (comp instanceof WorkComponent) {
            workComponents.add((WorkComponent) comp);
            ((WorkComponent) comp).setContainer(this);
        } else {
            LOGGER.warn(comp.getClass().getName() + " doesn't implement WorkComponent. Mouse events isn't available for it. ");
        }
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
    public void addToSelected(WorkComponent workComponent, InputEvent e) {
        if (!workComponent.canBeSelected(e)) {
            return;
        }
        if (e.getID() == MouseEvent.MOUSE_CLICKED) {

            if (workComponent.isSelected()) {
                workComponent.setSelected(false);
                LOGGER.trace(workComponent+" has been unselected.");
            } else {
                workComponent.setSelected(true);
                LOGGER.trace(workComponent+" has been selected.");
            }
            workComponent.getComponent().repaint();
            if (!e.isShiftDown()) {
                for (WorkComponent component : workComponents) {
                    if ((component != workComponent) && (component.isSelected())) {
                        component.setSelected(false);
                        LOGGER.trace(component+" has been unselected.");
                        component.getComponent().repaint();
                    }
                }
            }

        }
    }

    @Override
    public void deleteComponent(WorkComponent workComponent) {
        if (workComponent == null) {
            throw new IllegalArgumentException(new NullPointerException("'workComponent' can't be null."));
        }
        workComponent.delete();
        remove(workComponent.getComponent());
        workComponents.remove(workComponent);
        LOGGER.trace(workComponent+" has been deleted.");
    }


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
            setFocusable(true);
            addMouseListener(this);
            addMouseMotionListener(this);
            if (this.workComponents == null) {
                this.workComponents = new LinkedList<WorkComponent>();
            }
            getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "DELETE_SELECTED");
            getActionMap().put("DELETE_SELECTED", deleteAction);
        }

        private AbstractAction deleteAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOGGER.trace("Trying to delete selected components.");
                WorkComponent[] components = new WorkComponent[workComponents.size()];
                workComponents.toArray(components);
                for (WorkComponent workComponent : components) {
                    if (workComponent.isSelected()) {
                        deleteComponent(workComponent);
                    }
                }
                repaint();
            }
        };

        @Override
        public void mouseClicked(MouseEvent e) {
            requestFocus();
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

                                addToSelected(workComponent, e);
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
