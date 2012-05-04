package com.editor.screen.dialog;

import com.editor.res.Properties;
import com.editor.visualcomponent.JCrossWay;
import sun.awt.VerticalBagLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Date: 03.05.12
 * Time: 16:26
 *
 * @author: Alexey
 */
public class JCrossWayCreateDialog extends JDialog {
    private static final boolean DIALOG_AUTO_POSITION = Properties.getBoolean(Properties.Settings.DIALOG_AUTO_POSITION);

    private static final int DIALOG_WIDTH = Properties.getInt(Properties.Settings.JCW_CREATE_DIALOG_WIDTH);
    private static final int DIALOG_BORDER_WIDTH = Properties.getInt(Properties.Settings.JCW_CREATE_DIALOG_BORDER_WIDTH);
    private static final int DIALOG_HEIGHT = Properties.getInt(Properties.Settings.JCW_CREATE_DIALOG_HEIGHT);
    private static final int DIALOG_WITHOUT_POSITION_HEIGHT = Properties.getInt(Properties.Settings.JCW_CREATE_DIALOG_WITHOUT_POSITION_HEIGHT);

    private static final double DIALOG_SPINNER_STEP = Properties.getDouble(Properties.Settings.JCW_CREATE_DIALOG_SPINNER_STEP);
    private static final int DIALOG_COMPONENT_WIDTH = Properties.getInt(Properties.Settings.JCW_CREATE_DIALOG_COMPONENT_WIDTH);
    private static final int DIALOG_COMPONENT_HEIGHT = Properties.getInt(Properties.Settings.JCW_CREATE_DIALOG_COMPONENT_HEIGHT);

    public static final String DIALOG_ROAD_NUMBER_LABEL = Properties.getLabel(Properties.Labels.JCW_CREATE_DIALOG_ROAD_NUMBER_LABEL);
    public static final String DIALOG_POSITION_LABEL = Properties.getLabel(Properties.Labels.JCW_CREATE_DIALOG_POSITION_LABEL);
    private static final String DIALOG_ANGLE_LABEL = Properties.getLabel(Properties.Labels.JCW_CREATE_DIALOG_ANGLE_LABEL);
    private static final String DIALOG_SCALE_LABEL = Properties.getLabel(Properties.Labels.JCW_CREATE_DIALOG_SCALE_LABEL);
    private static final String OK_BUTTON_LABEL = Properties.getLabel(Properties.Labels.OK_BUTTON_LABEL);
    private static final String CANCEL_BUTTON_LABEL = Properties.getLabel(Properties.Labels.CANCEL_BUTTON_LABEL);


    private JSpinner positionX;
    private JSpinner positionY;
    private JSpinner placesCountSpinner;
    private JSpinner angleSpinner;
    private JSpinner scaleSpinner;


    private int x;
    private int y;
    private int placesCount;
    private double angle;
    private double scale;
    private JCrossWay jCrossWay = null;
    private boolean writePosition;


    JCrossWayCreateDialog(String title, int placesCount, double angle, double scale) {
        writePosition = false;
        init(title, x, y, placesCount, angle, scale, writePosition);
    }

    JCrossWayCreateDialog(String title, int x, int y, int placesCount, double angle, double scale) {
        writePosition = true;
        init(title, x, y, placesCount, angle, scale, writePosition);
    }

    private void init(String title, int x, int y, int placesCount, double angle, double scale, boolean writePosition) {
        setTitle(title);
        if (placesCount < 1) {
            throw new IllegalArgumentException("'placesCount' should be great than 0.");
        }
        if (scale < 0) {
            throw new IllegalArgumentException("'scale' can't be less than 0.");
        }
        JPanel topPanel = new JPanel(new VerticalBagLayout());

        //TODO add spinner settings to properties file
        angleSpinner = new JSpinner(new SpinnerNumberModel(angle, -Double.MAX_VALUE, Double.MAX_VALUE, DIALOG_SPINNER_STEP));
        scaleSpinner = new JSpinner(new SpinnerNumberModel(scale, 0, Double.MAX_VALUE, DIALOG_SPINNER_STEP));
        placesCountSpinner = new JSpinner(new SpinnerNumberModel(placesCount, 1, Integer.MAX_VALUE, 1));

        if (writePosition) {
            JLabel jPositionLabel = new JLabel(DIALOG_POSITION_LABEL);
            jPositionLabel.setPreferredSize(new Dimension(DIALOG_COMPONENT_WIDTH, DIALOG_COMPONENT_HEIGHT));
            topPanel.add(jPositionLabel);

            positionX = new JSpinner(new SpinnerNumberModel(x, -Integer.MAX_VALUE, Integer.MAX_VALUE, 1));
            positionY = new JSpinner(new SpinnerNumberModel(y, -Integer.MAX_VALUE, Integer.MAX_VALUE, 1));

            JPanel positionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            positionPanel.add(positionX);
            positionPanel.add(positionY);
            topPanel.add(positionPanel);
        }

        JLabel jPlaceCountLabel = new JLabel(DIALOG_ROAD_NUMBER_LABEL);
        jPlaceCountLabel.setPreferredSize(new Dimension(DIALOG_COMPONENT_WIDTH, DIALOG_COMPONENT_HEIGHT));
        topPanel.add(jPlaceCountLabel);
        topPanel.add(placesCountSpinner);

        JLabel jAngleLabel = new JLabel(DIALOG_ANGLE_LABEL);
        jAngleLabel.setPreferredSize(new Dimension(DIALOG_COMPONENT_WIDTH, DIALOG_COMPONENT_HEIGHT));
        topPanel.add(jAngleLabel);
        topPanel.add(angleSpinner);

        JLabel jScaleLabel = new JLabel(DIALOG_SCALE_LABEL);
        jScaleLabel.setPreferredSize(new Dimension(DIALOG_COMPONENT_WIDTH, DIALOG_COMPONENT_HEIGHT));
        topPanel.add(jScaleLabel);
        topPanel.add(scaleSpinner);
        JButton okButton = new JButton(OK_BUTTON_LABEL);
        okButton.setPreferredSize(new Dimension(DIALOG_COMPONENT_WIDTH, DIALOG_COMPONENT_HEIGHT));
        okButton.addActionListener(okActionListener);

        JButton cancelButton = new JButton(CANCEL_BUTTON_LABEL);
        cancelButton.setPreferredSize(new Dimension(DIALOG_COMPONENT_WIDTH, DIALOG_COMPONENT_HEIGHT));
        cancelButton.addActionListener(cancelActionListener);


        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(okButton);
        panel.add(cancelButton);
        topPanel.add(panel);


        int height;
        if (writePosition) {
            height = DIALOG_HEIGHT;
        } else {
            height = DIALOG_WITHOUT_POSITION_HEIGHT;
        }
        topPanel.setBounds(DIALOG_BORDER_WIDTH, DIALOG_BORDER_WIDTH, DIALOG_WIDTH - DIALOG_BORDER_WIDTH * 2, height - DIALOG_BORDER_WIDTH * 2);


        getContentPane().setLayout(null);
        getContentPane().add(topPanel);

        setSize(DIALOG_WIDTH, height + DIALOG_BORDER_WIDTH);
        //setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        setResizable(false);
        getRootPane().setDefaultButton(okButton);
        getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "CANCEL");
        getRootPane().getActionMap().put("CANCEL", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
                jCrossWay = null;
            }
        });
        //getRootPane().set
        setModal(true);
    }

    private ActionListener okActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            close();
            jCrossWay = new JCrossWay(placesCount, angle, scale);
            jCrossWay.setLocation(x, y);
        }
    };

    private ActionListener cancelActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            close();
            jCrossWay = null;
        }
    };

    private void close() {
        setVisible(false);
        angle = (Double) angleSpinner.getValue();
        scale = (Double) scaleSpinner.getValue();
        placesCount = (Integer) placesCountSpinner.getValue();
        if (writePosition) {
            x = (Integer) positionX.getValue();
            y = (Integer) positionY.getValue();
        } else {
            x = 0;
            y = 0;
        }
    }

    public JCrossWay getResult() {
        return jCrossWay;
    }

    public static JCrossWay showDialog(String title, int placesCount, double angle, double scale) {
        JCrossWayCreateDialog dialog = new JCrossWayCreateDialog(title, placesCount, angle, scale);
        if (DIALOG_AUTO_POSITION) {
            dialog.setLocation(MouseInfo.getPointerInfo().getLocation().x - dialog.getWidth() / 2, MouseInfo.getPointerInfo().getLocation().y - dialog.getHeight() / 2);
        }
        dialog.setVisible(true);

        return dialog.getResult();
    }

    public static JCrossWay showDialog(String title, int x, int y, int placesCount, double angle, double scale) {
        JCrossWayCreateDialog dialog = new JCrossWayCreateDialog(title, x, y, placesCount, angle, scale);
        if (DIALOG_AUTO_POSITION) {
            dialog.setLocation(MouseInfo.getPointerInfo().getLocation().x - dialog.getWidth() / 2, MouseInfo.getPointerInfo().getLocation().y - dialog.getHeight() / 2);
        }
        dialog.setVisible(true);

        return dialog.getResult();
    }
}
