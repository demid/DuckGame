package com.editor.screen;

import com.editor.map.Map;
import com.editor.res.Properties;
import com.editor.visualcomponent.JGridPanel;
import com.editor.visualcomponent.toolbarbutton.JCrossWayToolBarButton;
import com.editor.visualcomponent.toolbarbutton.JToolBarButton;
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
import java.util.LinkedList;
import java.util.List;

/**
 * Date: 24.04.12
 * Time: 15:04
 *
 * @author: Alexey
 */
public class JMapEditScreen extends JFrame {
    private final static Logger LOGGER = Logger.getLogger(JMapEditScreen.class);

    private static String WIDTH_SLIDER_LABEL = Properties.getLabel(Properties.Labels.JMES_WIDTH_SLIDER_LABEL);
    private static String HEIGHT_SLIDER_LABEL = Properties.getLabel(Properties.Labels.JMES_HEIGHT_SLIDER_LABEL);
    private static String DRAW_GRID_LABEL = Properties.getLabel(Properties.Labels.JMES_DRAW_GRID_LABEL);
    private static String GRID_SIZE_LABEL = Properties.getLabel(Properties.Labels.JMES_GRID_SIZE_LABEL);
    private static String WORK_ARIA_SETTINGS_LABEL = Properties.getLabel(Properties.Labels.JMES_WORK_ARIA_SETTINGS_LABEL);
    private static String WORK_ARIA_MAP_OBJECTS_LABEL = Properties.getLabel(Properties.Labels.JMES_WORK_ARIA_MAP_OBJECTS_LABEL);

    private static int WIDTH = Properties.getInt(Properties.Settings.JMES_WIDTH);
    private static int HEIGHT = Properties.getInt(Properties.Settings.JMES_HEIGHT);
    private static int MIN_WIDTH = Properties.getInt(Properties.Settings.JMES_MIN_WIDTH);
    private static int MIN_HEIGHT = Properties.getInt(Properties.Settings.JMES_MIN_HEIGHT);


    private static int STARTUP_SATE = Properties.getInt(Properties.Settings.JMES_STARTUP_SATE);
    private static int SPINNER_STEP = Properties.getInt(Properties.Settings.JMES_SPINNER_STEP);
    private static int CELL_SIZE = Properties.getInt(Properties.Settings.JMES_CELL_SIZE);
    private static boolean DRAW_GREED = Properties.getBoolean(Properties.Settings.JMES_DRAW_GREED);
    private static int CELL_BORDER_WIDTH = Properties.getInt(Properties.Settings.JMES_CELL_BORDER_WIDTH);
    private static int COMPONENT_WIDTH = Properties.getInt(Properties.Settings.JMES_COMPONENT_WIDTH);
    private static int COMPONENT_HEIGHT = Properties.getInt(Properties.Settings.JMES_COMPONENT_HEIGHT);
    private static int MAP_OBJECT_BUTTON_HEIGHT = Properties.getInt(Properties.Settings.JMES_MAP_OBJECT_BUTTON_HEIGHT);
    private static int MAP_OBJECT_BUTTON_WIDTH = Properties.getInt(Properties.Settings.JMES_MAP_OBJECT_BUTTON_WIDTH);


    private JGridPanel workAria = new JGridPanel();
    private JPanel containerWorkAria = new JPanel();
    private JPanel toolBarButtonsPanel = new JPanel(new VerticalBagLayout());


    private Map map;
    private JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, SPINNER_STEP));
    private JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, SPINNER_STEP));
    private JSpinner gridSizeSpinner = new JSpinner(new SpinnerNumberModel(CELL_SIZE, 1, Integer.MAX_VALUE, SPINNER_STEP));
    //==================
    private JCheckBox drawGrid = new JCheckBox(DRAW_GRID_LABEL, DRAW_GREED);
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

        setExtendedState(STARTUP_SATE);
        setSize(WIDTH, HEIGHT);
        setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));

        //workAria initialization
        workAria.setBorderCellWidth(CELL_BORDER_WIDTH);
        workAria.setCellSize(CELL_SIZE);
        workAria.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        //containerWorkAria initialization
        containerWorkAria.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        containerWorkAria.setLayout(new GridBagLayout());
        //toolBar initialization


        drawGrid.addActionListener(drawGridListener);

        workAria.setLayout(null);
        workAria.setDrawGrid(DRAW_GREED);

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

        widthSpinner.setPreferredSize(new Dimension(COMPONENT_WIDTH, COMPONENT_HEIGHT));
        widthSpinner.addChangeListener(sizeChangeListener);
        widthSpinner.setEnabled(false);

        JLabel widthSpinnerLabel = new JLabel(WIDTH_SLIDER_LABEL);
        widthSpinnerLabel.setPreferredSize(new Dimension(COMPONENT_WIDTH, COMPONENT_HEIGHT));


        heightSpinner.setPreferredSize(new Dimension(COMPONENT_WIDTH, COMPONENT_HEIGHT));
        heightSpinner.addChangeListener(sizeChangeListener);
        heightSpinner.setEnabled(false);

        JLabel heightSpinnerLabel = new JLabel(HEIGHT_SLIDER_LABEL);
        heightSpinnerLabel.setPreferredSize(new Dimension(COMPONENT_WIDTH, COMPONENT_HEIGHT));

        gridSizeSpinner.setPreferredSize(new Dimension(COMPONENT_WIDTH, COMPONENT_HEIGHT));
        gridSizeSpinner.addChangeListener(gridSizeChangeListener);
        JLabel gridSizeSpinnerLabel = new JLabel(GRID_SIZE_LABEL);
        gridSizeSpinnerLabel.setPreferredSize(new Dimension(COMPONENT_WIDTH, COMPONENT_HEIGHT));


        drawGrid.setPreferredSize(new Dimension(COMPONENT_WIDTH, COMPONENT_HEIGHT));
        toolBar.add(drawGrid);


        JPanel workAriaSettingsPanel = new JPanel();
        workAriaSettingsPanel.setBorder(new TitledBorder(WORK_ARIA_SETTINGS_LABEL));
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

    private ActionListener createJCrossWayAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (getMap() != null) {

            }
        }
    };


    private JPanel addMapObjectsButtons(JPanel toolBar) {

        JPanel mapObjectsPanel = new JPanel();
        mapObjectsPanel.setBorder(new TitledBorder(WORK_ARIA_MAP_OBJECTS_LABEL));
        mapObjectsPanel.add(toolBarButtonsPanel);
        toolBar.add(mapObjectsPanel);
        return toolBar;
    }

    public void addToolBarButtons(List<JToolBarButton> buttonList) {
        for (JToolBarButton jToolBarButton : buttonList) {
            jToolBarButton.setPreferredSize(new Dimension(MAP_OBJECT_BUTTON_HEIGHT, MAP_OBJECT_BUTTON_WIDTH));
            toolBarButtonsPanel.add(jToolBarButton);
        }
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

    public JGridPanel getWorkAria() {
        return workAria;
    }

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

    public static void createDefaultButtons(JMapEditScreen jMapEditScreen) {
        LinkedList<JToolBarButton> jToolBarButtons = new LinkedList<JToolBarButton>();
        jToolBarButtons.add(new JCrossWayToolBarButton(jMapEditScreen.getWorkAria()));
        jMapEditScreen.addToolBarButtons(jToolBarButtons);
    }

}
