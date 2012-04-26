package com.editor.screen;

import com.editor.map.Map;
import com.editor.res.Properties;
import com.editor.visualcomponent.JCrossWay;
import com.editor.visualcomponent.JGridPanel;
import com.game.roadnetwork.GeographicCrossWay;
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
    private JSpinner gridSizeSpinner = new JSpinner(new SpinnerNumberModel( Properties.getInt(Properties.Settings.EDIT_SCREEN_CELL_SIZE), 1, Integer.MAX_VALUE, Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_STEP)));
    //==================
    private JButton crossWayButton = new JButton(Properties.getIcon(Properties.Settings.CROSS_WAY_BUTTON_ICO));

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


        drawGrid.addChangeListener(drawGridListener);

        workAria.setLayout(null);

        /*JCrossWay jCrossWay = new JCrossWay(6,200,0);
        jCrossWay.setPosition(10,10);

        workAria.add(jCrossWay);*/
        JCrossWay crossWay = new JCrossWay(new GeographicCrossWay(6,10,10,0,100));
        workAria.add(crossWay);
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

          widthSpinner.setPreferredSize(new Dimension(Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_WIDTH),
                Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_HEIGHT)));
        widthSpinner.addChangeListener(sizeChangeListener);
        widthSpinner.setEnabled(false);

        JLabel widthSpinnerLabel = new JLabel(Properties.getLabel(Properties.Labels.WIDTH_SLIDER_TITLE));
        widthSpinnerLabel.setPreferredSize(new Dimension(Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_WIDTH),
                Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_HEIGHT)));


        heightSpinner.setPreferredSize(new Dimension(Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_WIDTH),
                Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_HEIGHT)));
        heightSpinner.addChangeListener(sizeChangeListener);
        heightSpinner.setEnabled(false);

        JLabel heightSpinnerLabel = new JLabel(Properties.getLabel(Properties.Labels.HEIGHT_SLIDER_TITLE));
        heightSpinnerLabel.setPreferredSize(new Dimension(Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_WIDTH),
                Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_HEIGHT)));


        gridSizeSpinner.setPreferredSize(new Dimension(Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_WIDTH),
                Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_HEIGHT)));
        gridSizeSpinner.addChangeListener(gridSizeChangeListener);
        JLabel gridSizeSpinnerLabel = new JLabel(Properties.getLabel(Properties.Labels.GRID_SIZE_TITLE));
        gridSizeSpinnerLabel.setPreferredSize(new Dimension(Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_WIDTH),
                Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_HEIGHT)));


        drawGrid.setPreferredSize(new Dimension(Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_WIDTH),
                Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_HEIGHT)));
        toolBar.add(drawGrid);



        JPanel workAriaSettingsPanel = new JPanel();
        workAriaSettingsPanel.setBorder(new TitledBorder(Properties.getLabel(Properties.Labels.WORK_ARIA_SETTINGS_TITLE)));
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

    private JPanel addMapObjectsButtons(JPanel toolBar){
        crossWayButton.setPreferredSize(new Dimension(Properties.getInt(Properties.Settings.MAP_OBJECT_BUTTON_WIDTH),
                Properties.getInt(Properties.Settings.MAP_OBJECT_BUTTON_HEIGHT)));

        JPanel mapObjectsPanel = new JPanel();
        mapObjectsPanel.setBorder(new TitledBorder(Properties.getLabel(Properties.Labels.WORK_ARIA_MAP_OBJECTS_TITLE)));
        JPanel innerPanel = new JPanel(new VerticalBagLayout());

        innerPanel.add(crossWayButton);
        mapObjectsPanel.add(innerPanel);
        toolBar.add(mapObjectsPanel);
        return toolBar;
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
        workAria.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
        workAria.setMinimumSize(new Dimension(map.getWidth(), map.getHeight()));

        containerWorkAria.updateUI();
        heightSpinner.setValue(map.getHeight());
        heightSpinner.setEnabled(true);
        widthSpinner.setValue(map.getWidth());
        widthSpinner.setEnabled(true);
    }
}
