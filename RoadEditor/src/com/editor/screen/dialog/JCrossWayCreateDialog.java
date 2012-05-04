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
    private static final int DIALOG_WIDTH = Properties.getInt(Properties.Settings.JCW_CREATE_DIALOG_WIDTH);
    private static final int DIALOG_BORDER_WIDTH = Properties.getInt(Properties.Settings.JCW_CREATE_DIALOG_BORDER_WIDTH);
    private static final int DIALOG_HEIGHT = Properties.getInt(Properties.Settings.JCW_CREATE_DIALOG_HEIGHT);
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

    JCrossWayCreateDialog(String title, int x, int y, int placesCount, double angle, double scale) {
        setTitle(title);
        this.x = x;
        this.y = y;
        this.placesCount = placesCount;
        this.angle = angle;
        this.scale = scale;
        if (placesCount < 1) {
            throw new IllegalArgumentException("'placesCount' should be great than 0.");
        }
        if (scale < 0) {
            throw new IllegalArgumentException("'scale' can't be less than 0.");
        }
        JPanel topPanel = new JPanel(new VerticalBagLayout());
        JLabel jPositionLabel = new JLabel(DIALOG_POSITION_LABEL);
        jPositionLabel.setPreferredSize(new Dimension(DIALOG_COMPONENT_WIDTH, DIALOG_COMPONENT_HEIGHT));
        topPanel.add(jPositionLabel);

        //TODO add spinner settings to properties file
        angleSpinner = new JSpinner(new SpinnerNumberModel(angle, -Double.MAX_VALUE, Double.MAX_VALUE, DIALOG_SPINNER_STEP));
        scaleSpinner = new JSpinner(new SpinnerNumberModel(scale, 0, Double.MAX_VALUE, DIALOG_SPINNER_STEP));
        placesCountSpinner = new JSpinner(new SpinnerNumberModel(placesCount, 1, Integer.MAX_VALUE, 1));
        positionX = new JSpinner(new SpinnerNumberModel(x, -Integer.MAX_VALUE, Integer.MAX_VALUE, 1));
        positionY = new JSpinner(new SpinnerNumberModel(y, -Integer.MAX_VALUE, Integer.MAX_VALUE, 1));

        JPanel positionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        positionPanel.add(positionX);
        positionPanel.add(positionY);
        topPanel.add(positionPanel);

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


        topPanel.setBounds(DIALOG_BORDER_WIDTH, DIALOG_BORDER_WIDTH, DIALOG_WIDTH - DIALOG_BORDER_WIDTH * 2, DIALOG_HEIGHT - DIALOG_BORDER_WIDTH * 2);

        getContentPane().setLayout(null);
        getContentPane().add(topPanel);

        setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
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
            jCrossWay = new JCrossWay((Integer) placesCountSpinner.getValue(), (Double) angleSpinner.getValue(), (Double) scaleSpinner.getValue());
            jCrossWay.setPosition((Integer) positionX.getValue(), (Integer) positionY.getValue());
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
    }

    public JCrossWay getResult() {
        return jCrossWay;
    }

    public static JCrossWay showDialog(String title, int x, int y, int placesCount, double angle, double scale) {
        JCrossWayCreateDialog dialog = new JCrossWayCreateDialog(title, x, y, placesCount, angle, scale);
        dialog.setVisible(true);
        return dialog.getResult();
    }
}
