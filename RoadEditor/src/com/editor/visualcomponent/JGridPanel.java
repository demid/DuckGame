package com.editor.visualcomponent;

import javax.swing.*;
import java.awt.*;

/**
 * Date: 24.04.12
 * Time: 10:36
 *
 * @author: Alexey
 */
public class JGridPanel extends JPanel {
    public static final int DEFAULT_CELL_SIZE = 5;
    public static final int DEFAULT_BORDER_CELL_WIDTH = 1;
    public static final Color DEFAULT_BORDER_CELL_COLOR = Color.BLACK;


    private int cellSize = DEFAULT_CELL_SIZE;
    private int borderCellWidth = DEFAULT_BORDER_CELL_WIDTH;
    private Color gridColor = DEFAULT_BORDER_CELL_COLOR;
    private boolean drawGrid = true;



    public JGridPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public JGridPanel(LayoutManager layout) {
        super(layout);
    }

    public JGridPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public JGridPanel() {
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
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
}
