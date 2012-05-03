package com.editor.screen;

import com.editor.map.Map;
import com.editor.res.Properties;
import com.editor.visualcomponent.JCrossWay;
import com.editor.visualcomponent.JGridPanel;
import com.editor.visualcomponent.JRoad;
import com.game.roadnetwork.Direction;
import com.game.roadnetwork.Way;
import org.apache.log4j.Logger;
import sun.awt.VerticalBagLayout;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Date: 24.04.12
 * Time: 15:04
 *
 * @author: Alexey
 */
public class JMapEditScreen extends JFrame {
    private final static Logger LOGGER = Logger.getLogger(JMapEditScreen.class);

    private String widthSliderLabel = Properties.getLabel(Properties.Labels.JMES_WIDTH_SLIDER_LABEL);
    private String heightSliderLabel = Properties.getLabel(Properties.Labels.JMES_HEIGHT_SLIDER_LABEL);
    private String drawGridLabel = Properties.getLabel(Properties.Labels.JMES_DRAW_GRID_LABEL);
    private String gridSizeLabel = Properties.getLabel(Properties.Labels.JMES_GRID_SIZE_LABEL);
    private String workAriaSettingsLabel = Properties.getLabel(Properties.Labels.JMES_WORK_ARIA_SETTINGS_LABEL);
    private String workAriaMapObjectsLabel = Properties.getLabel(Properties.Labels.JMES_WORK_ARIA_MAP_OBJECTS_LABEL);

    private int width = Properties.getInt(Properties.Settings.JMES_WIDTH);
    private int height = Properties.getInt(Properties.Settings.JMES_HEIGHT);
    private int minWidth = Properties.getInt(Properties.Settings.JMES_MIN_WIDTH);
    private int minHeight = Properties.getInt(Properties.Settings.JMES_MIN_HEIGHT);


    private int startupState = Properties.getInt(Properties.Settings.JMES_STARTUP_SATE);
    private int spinnerStep = Properties.getInt(Properties.Settings.JMES_SPINNER_STEP);
    private int cellSize = Properties.getInt(Properties.Settings.JMES_CELL_SIZE);
    private boolean drawGreed = Properties.getBoolean(Properties.Settings.JMES_DRAW_GREED);
    private int cellBorderWidth = Properties.getInt(Properties.Settings.JMES_CELL_BORDER_WIDTH);
    private int componentWidth = Properties.getInt(Properties.Settings.JMES_COMPONENT_WIDTH);
    private int componentHeight = Properties.getInt(Properties.Settings.JMES_COMPONENT_HEIGHT);
    private int mapObjectButtonWidth = Properties.getInt(Properties.Settings.JMES_MAP_OBJECT_BUTTON_HEIGHT);
    private int mapObjectButtonHeight = Properties.getInt(Properties.Settings.JMES_MAP_OBJECT_BUTTON_WIDTH);


    private Icon wayButtonIcon =  Properties.getIcon(Properties.Settings.JMES_CROSS_WAY_BUTTON_ICO);

    private JGridPanel workAria = new JGridPanel();
    private JPanel containerWorkAria = new JPanel();
    private Map map;
    private JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, spinnerStep));
    private JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, spinnerStep));
    private JSpinner gridSizeSpinner = new JSpinner(new SpinnerNumberModel(cellSize, 1, Integer.MAX_VALUE,spinnerStep));
    //==================
    private JButton crossWayButton = new JButton(wayButtonIcon);
    private JCheckBox drawGrid = new JCheckBox(drawGridLabel, drawGreed);
    //==================


    public JMapEditScreen() throws HeadlessException {
        initialization();
    }

    public JMapEditScreen(GraphicsConfiguration gc) {
        super(gc);
        initialization();
    }

    public JMapEditScreen(String title) throws HeadlessException {
        super(title);
        initialization();
    }

    public JMapEditScreen(String title, GraphicsConfiguration gc) {
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


        addWindowListener(new BasicWindowMonitor());

        setExtendedState(startupState);
        setSize(width, height);
        setMinimumSize(new Dimension(minWidth,minHeight));

        //workAria initialization
        workAria.setBorderCellWidth(cellBorderWidth);
        workAria.setCellSize(cellSize);
        workAria.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        //containerWorkAria initialization
        containerWorkAria.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        containerWorkAria.setLayout(new GridBagLayout());
        //toolBar initialization


        drawGrid.addActionListener(drawGridListener);

        workAria.setLayout(null);

        /*JCrossWay jCrossWay = new JCrossWay(6,200,0);
        jCrossWay.setPosition(10,10);

        workAria.add(jCrossWay);*/

        JRoad r1 = new JRoad(new Way(Direction.FORWARD), new Way(Direction.FORWARD), new Way(Direction.FORWARD), new Way(Direction.FORWARD));
        JRoad r2 = new JRoad(new Way(Direction.FORWARD), new Way(Direction.FORWARD));


        JCrossWay c1 = new JCrossWay(3, 0, 50);
        c1.setPosition(10, 10);
        //c1.attachRoad(0,r1,true);


        JCrossWay c2 = new JCrossWay(3, 0, 50);
        c2.setPosition(100, 100);
        c2.attachRoad(1, r1, false);
        c2.attachRoad(2, r2, true);


        JCrossWay c3 = new JCrossWay(3, 0, 50);
        c3.setPosition(400, 20);
        c3.attachRoad(1, r2, false);


        workAria.add(c1);
        //workAria.add(c2);
        //workAria.add(c3);

        //workAria.add(r1);
        //workAria.add(r2);

        containerWorkAria.add(workAria);

        JScrollPane jScrollPane = new JScrollPane(containerWorkAria);
        jScrollPane.setBorder(BorderFactory.createEmptyBorder());
        getContentPane().add(jScrollPane, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        panel.add(addMapObjectsButtons(toolBarInit()));
        getContentPane().add(panel, BorderLayout.EAST);


        LOGGER.trace("Screen has been created.");
    }


    private JPanel toolBarInit() {
        JPanel toolBar = new JPanel();
        toolBar.setLayout(new VerticalBagLayout());
        toolBar.setBorder(BorderFactory.createEmptyBorder());

        widthSpinner.setPreferredSize(new Dimension(componentWidth,componentHeight));
        widthSpinner.addChangeListener(sizeChangeListener);
        widthSpinner.setEnabled(false);

        JLabel widthSpinnerLabel = new JLabel(widthSliderLabel);
        widthSpinnerLabel.setPreferredSize(new Dimension(componentWidth,componentHeight));


        heightSpinner.setPreferredSize(new Dimension(componentWidth,componentHeight));
        heightSpinner.addChangeListener(sizeChangeListener);
        heightSpinner.setEnabled(false);

        JLabel heightSpinnerLabel = new JLabel(heightSliderLabel);
        heightSpinnerLabel.setPreferredSize(new Dimension(componentWidth,componentHeight));

        gridSizeSpinner.setPreferredSize(new Dimension(componentWidth,componentHeight));
        gridSizeSpinner.addChangeListener(gridSizeChangeListener);
        JLabel gridSizeSpinnerLabel = new JLabel(gridSizeLabel);
        gridSizeSpinnerLabel.setPreferredSize(new Dimension(componentWidth,componentHeight));


        drawGrid.setPreferredSize(new Dimension(componentWidth,componentHeight));
        toolBar.add(drawGrid);


        JPanel workAriaSettingsPanel = new JPanel();
        workAriaSettingsPanel.setBorder(new TitledBorder(workAriaSettingsLabel));
        JPanel innerPanel = new JPanel(new VerticalBagLayout());


        innerPanel.add(widthSpinnerLabel);
        innerPanel.add(widthSpinner);
        innerPanel.add(heightSpinnerLabel);
        innerPanel.add(heightSpinner);
        innerPanel.add(gridSizeSpinnerLabel);
        innerPanel.add(gridSizeSpinner);
        innerPanel.add(drawGrid);


        workAriaSettingsPanel.add(innerPanel);
        toolBar.add(workAriaSettingsPanel);

        return toolBar;
    }

    private JPanel addMapObjectsButtons(JPanel toolBar) {
        crossWayButton.setPreferredSize(new Dimension(mapObjectButtonWidth,mapObjectButtonHeight));

        JPanel mapObjectsPanel = new JPanel();
        mapObjectsPanel.setBorder(new TitledBorder(workAriaMapObjectsLabel));
        JPanel innerPanel = new JPanel(new VerticalBagLayout());

        innerPanel.add(crossWayButton);
        mapObjectsPanel.add(innerPanel);
        toolBar.add(mapObjectsPanel);
        return toolBar;
    }


    public Map getMap() {
        return map;
    }

    private ActionListener drawGridListener = new ActionListener() {


        @Override
        public void actionPerformed(ActionEvent e) {
            workAria.setDrawGrid(drawGrid.isSelected());
            containerWorkAria.updateUI();
        }
    };

    private ChangeListener gridSizeChangeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            if (e instanceof ChangeEvent) {
                workAria.setCellSize((Integer) gridSizeSpinner.getValue());
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
        //workAria.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
        //workAria.setMinimumSize(new Dimension(map.getWidth(), map.getHeight()));
        workAria.setSize(new Dimension(map.getWidth(), map.getHeight()));
        containerWorkAria.updateUI();
        heightSpinner.setValue(map.getHeight());
        heightSpinner.setEnabled(true);
        widthSpinner.setValue(map.getWidth());
        widthSpinner.setEnabled(true);
    }


}
