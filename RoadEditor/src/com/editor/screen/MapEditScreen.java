package com.editor.screen;

import com.editor.map.Map;
import com.editor.res.Properties;
import com.editor.visualcomponent.JGridPanel;
import org.apache.log4j.Logger;
import sun.awt.VerticalBagLayout;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Date: 24.04.12
 * Time: 15:04
 *
 * @author: Alexey
 */
public class MapEditScreen extends JFrame {
    private final static Logger LOGGER = Logger.getLogger(MapEditScreen.class);
    private JGridPanel workAria = new JGridPanel();
    private JPanel containerWorkAria = new JPanel();
    private Map map;
    private JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_STEP)));
    private JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_STEP)));
    private JCheckBox drawGrid = new JCheckBox(Properties.getLabel(Properties.Labels.DRAW_GRID_TITLE), Properties.getBoolean(Properties.Settings.EDIT_SCREEN_DRAW_GREED));

    public MapEditScreen() throws HeadlessException {
        initialization();
    }

    public MapEditScreen(GraphicsConfiguration gc) {
        super(gc);
        initialization();
    }

    public MapEditScreen(String title) throws HeadlessException {
        super(title);
        initialization();
    }

    public MapEditScreen(String title, GraphicsConfiguration gc) {
        super(title, gc);
        initialization();
    }

    private final void setAndCheckMap(Map map) {
        if (map == null) {
            throw new IllegalArgumentException(new NullPointerException("'map' can't be null."));
        }
        this.map = map;
    }

    private final void initialization() {
        JPanel toolBar = new JPanel();


        addWindowListener(new BasicWindowMonitor());
        setExtendedState(Properties.getInt(Properties.Settings.EDIT_SCREEN_SATE));
        setSize(Properties.getInt(Properties.Settings.EDIT_SCREEN_WIDTH), Properties.getInt(Properties.Settings.EDIT_SCREEN_HEIGHT));
        setMinimumSize(new Dimension(Properties.getInt(Properties.Settings.EDIT_SCREEN_MIN_WIDTH),
                Properties.getInt(Properties.Settings.EDIT_SCREEN_MIN_HEIGHT)));

        //workAria initialization
        workAria.setBorderCellWidth(Properties.getInt(Properties.Settings.EDIT_SCREEN_CELL_BORDER_WIDTH));
        workAria.setCellSize(Properties.getInt(Properties.Settings.EDIT_SCREEN_CELL_SIZE));
        workAria.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        //containerWorkAria initialization
        containerWorkAria.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        containerWorkAria.setLayout(new GridBagLayout());
        //toolBar initialization
        toolBar.setLayout(new VerticalBagLayout());
        toolBar.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        JPanel widthPanel = new JPanel();
        widthPanel.setBorder(new TitledBorder(Properties.getLabel(Properties.Labels.WIDTH_SLIDER_TITLE)));
        widthSpinner.setPreferredSize(new Dimension(Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_WIDTH),
                Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_HEIGHT)));
        widthPanel.add(widthSpinner);
        widthSpinner.addChangeListener(sizeChangeListener);
        toolBar.add(widthPanel);
        widthSpinner.setEnabled(false);

        JPanel heightPanel = new JPanel();
        heightPanel.setBorder(new TitledBorder(Properties.getLabel(Properties.Labels.HEIGHT_SLIDER_TITLE)));
        heightSpinner.setPreferredSize(new Dimension(Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_WIDTH),
                Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_HEIGHT)));
        heightPanel.add(heightSpinner);
        heightSpinner.setEnabled(false);
        heightSpinner.addChangeListener(sizeChangeListener);
        toolBar.add(heightPanel);

        drawGrid.setPreferredSize(new Dimension(Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_WIDTH),
                Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_HEIGHT)));
        toolBar.add(drawGrid);
        drawGrid.addChangeListener(drawGridListener);

        containerWorkAria.add(workAria);

        JScrollPane jScrollPane = new JScrollPane(containerWorkAria);
        jScrollPane.setBorder(BorderFactory.createEmptyBorder());
        getContentPane().add(jScrollPane, BorderLayout.CENTER);
        getContentPane().add(toolBar, BorderLayout.EAST);


        LOGGER.trace("Screen has been created.");
    }

    public Map getMap() {
        return map;
    }

    private ChangeListener drawGridListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            if (e instanceof ChangeEvent) {
                workAria.setDrawGrid(drawGrid.isSelected());
                containerWorkAria.updateUI();
            }
        }
    };

    private ChangeListener sizeChangeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            if (e instanceof ChangeEvent) {
                if (e.getSource() == heightSpinner) {
                    map.setHeight((Integer) heightSpinner.getValue());
                } else if (e.getSource() == widthSpinner) {
                    map.setWidth((Integer) widthSpinner.getValue());
                }
                workAria.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
                workAria.setMinimumSize(new Dimension(map.getWidth(), map.getHeight()));
                containerWorkAria.updateUI();
            }

        }
    };

    public void loadMap(Map map) {
        setAndCheckMap(map);
        workAria.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
        workAria.setMinimumSize(new Dimension(map.getWidth(), map.getHeight()));

        containerWorkAria.updateUI();
        heightSpinner.setValue(map.getHeight());
        heightSpinner.setEnabled(true);
        widthSpinner.setValue(map.getWidth());
        widthSpinner.setEnabled(true);
    }
}
