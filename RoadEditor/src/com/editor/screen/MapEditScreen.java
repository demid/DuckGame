package com.editor.screen;

import com.editor.map.Map;
import com.editor.res.Properties;
import com.editor.visualcomponent.GridPanel;
import org.apache.log4j.Logger;
import sun.awt.VerticalBagLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Date: 24.04.12
 * Time: 15:04
 *
 * @author: Alexey
 */
public class MapEditScreen extends JFrame {
    private final static Logger LOGGER = Logger.getLogger(MapEditScreen.class);
    private GridPanel workAria = new GridPanel();
    private JSlider widthSlider = new JSlider(0,Properties.getInt(Properties.Settings.EDIT_SCREEN_SLIDER_MAX_VALUE));
    private JSlider heightSlider = new JSlider(0,Properties.getInt(Properties.Settings.EDIT_SCREEN_SLIDER_MAX_VALUE));
    private JPanel containerWorkAria = new JPanel();
    private Map map;

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
        workAria.setBorder(new LineBorder(Color.GREEN));

        //containerWorkAria initialization
        containerWorkAria.setBorder(new LineBorder(Color.BLACK));
        containerWorkAria.setLayout(new GridBagLayout());
        containerWorkAria.setBackground(Color.GRAY);
        //toolBar initialization
        toolBar.setLayout(new VerticalBagLayout());
        JPanel widthPanel  = new JPanel();
        widthPanel.setBorder(new TitledBorder(Properties.getLabel(Properties.Labels.WIDTH_SLIDER_TITLE)));
        widthPanel.add(widthSlider);
        toolBar.add(widthPanel);
        widthSlider.setMajorTickSpacing(Properties.getInt(Properties.Settings.EDIT_SCREEN_SLIDER_MAJOR_TRICK_SPACING));
        widthSlider.setMinorTickSpacing(Properties.getInt(Properties.Settings.EDIT_SCREEN_SLIDER_MINOR_TRICK_SPACING));
        widthSlider.setPaintTicks(true);
        widthSlider.setPaintLabels(true);
        widthSlider.setPaintTrack(true);
        widthSlider.setEnabled(false);


        JPanel heightPanel  = new JPanel();
        heightPanel.setBorder(new TitledBorder(Properties.getLabel(Properties.Labels.HEIGHT_SLIDER_TITLE)));
        heightPanel.add(heightSlider);
        toolBar.add(heightPanel);
        heightSlider.setMajorTickSpacing(Properties.getInt(Properties.Settings.EDIT_SCREEN_SLIDER_MAJOR_TRICK_SPACING));
        heightSlider.setMinorTickSpacing(Properties.getInt(Properties.Settings.EDIT_SCREEN_SLIDER_MINOR_TRICK_SPACING));
        heightSlider.setPaintTicks(true);
        heightSlider.setPaintLabels(true);
        heightSlider.setPaintTrack(true);
        heightSlider.setEnabled(false);

        toolBar.setBorder(new LineBorder(Color.BLACK));


        containerWorkAria.add(workAria);

        getContentPane().add(new JScrollPane(containerWorkAria), BorderLayout.CENTER);
        getContentPane().add(toolBar, BorderLayout.EAST);


        LOGGER.trace("Screen has been created.");
    }

    public Map getMap() {
        return map;
    }

    public void loadMap(Map map) {
        setAndCheckMap(map);
        workAria.setPreferredSize(new Dimension(map.getWidth(), map.getHeight()));
        workAria.setMinimumSize(new Dimension(map.getWidth(), map.getHeight()));
        containerWorkAria.updateUI();
        widthSlider.setEnabled(true);
        widthSlider.setValue(map.getWidth());
        heightSlider.setEnabled(true);
        heightSlider.setValue(map.getHeight());

    }
}
