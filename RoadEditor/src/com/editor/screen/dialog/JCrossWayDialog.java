package com.editor.screen.dialog;

import com.editor.res.Properties;
import sun.awt.VerticalBagLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Date: 30.04.12
 * Time: 14:38
 *
 * @author: Alexey
 */
public class JCrossWayDialog extends JDialog {
    //Settings from properties file
    private static final int DIALOG_WIDTH = Properties.getInt(Properties.Settings.JCW_DIALOG_WIDTH);
    private static final int DIALOG_BORDER_WIDTH = Properties.getInt(Properties.Settings.JCW_DIALOG_BORDER_WIDTH);
    private static final int DIALOG_HEIGHT = Properties.getInt(Properties.Settings.JCW_DIALOG_HEIGHT);
    private static final double DIALOG_SPINNER_STEP = Properties.getDouble(Properties.Settings.JCW_DIALOG_SPINNER_STEP);
    private static final int DIALOG_COMPONENT_WIDTH = Properties.getInt(Properties.Settings.JCW_DIALOG_COMPONENT_WIDTH);
    private static final int DIALOG_COMPONENT_HEIGHT = Properties.getInt(Properties.Settings.JCW_DIALOG_COMPONENT_HEIGHT);

    private static final String DIALOG_ANGLE_LABEL = Properties.getLabel(Properties.Labels.JCW_DIALOG_ANGLE_LABEL);
    private static final String DIALOG_SCALE_LABEL = Properties.getLabel(Properties.Labels.JCW_DIALOG_SCALE_LABEL);
    private static final String OK_BUTTON_LABEL = Properties.getLabel(Properties.Labels.OK_BUTTON_LABEL);
    private static final String CANCEL_BUTTON_LABEL = Properties.getLabel(Properties.Labels.CANCEL_BUTTON_LABEL);


    private JSpinner angleSpinner;
    private JSpinner scaleSpinner;
    private double scale;
    private double angle;
    private boolean result = false;


    public JCrossWayDialog(String title, double angle, double scale) {
        setTitle(title);
        JPanel innerPanel = new JPanel(new VerticalBagLayout());
        angleSpinner = new JSpinner(new SpinnerNumberModel(angle, -Double.MAX_VALUE, Double.MAX_VALUE, DIALOG_SPINNER_STEP));
        scaleSpinner = new JSpinner(new SpinnerNumberModel(scale, 0, Double.MAX_VALUE, DIALOG_SPINNER_STEP));
        JLabel angleLabel = new JLabel(DIALOG_ANGLE_LABEL);
        angleLabel.setPreferredSize(new Dimension(DIALOG_COMPONENT_WIDTH, DIALOG_COMPONENT_HEIGHT));
        innerPanel.add(angleLabel);
        innerPanel.add(angleSpinner);
        JLabel scaleLabel = new JLabel(DIALOG_SCALE_LABEL);
        scaleLabel.setPreferredSize(new Dimension(DIALOG_COMPONENT_WIDTH, DIALOG_COMPONENT_HEIGHT));
        innerPanel.add(scaleLabel);
        innerPanel.add(scaleSpinner);
        JButton okButton = new JButton(OK_BUTTON_LABEL);
        okButton.setPreferredSize(new Dimension(DIALOG_COMPONENT_WIDTH, DIALOG_COMPONENT_HEIGHT));
        okButton.addActionListener(okActionListener);

        JButton cancelButton = new JButton(CANCEL_BUTTON_LABEL);
        cancelButton.setPreferredSize(new Dimension(DIALOG_COMPONENT_WIDTH, DIALOG_COMPONENT_HEIGHT));
        cancelButton.addActionListener(cancelActionListener);


        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(okButton);
        panel.add(cancelButton);
        innerPanel.add(panel);


        innerPanel.setBounds(DIALOG_BORDER_WIDTH, DIALOG_BORDER_WIDTH, DIALOG_WIDTH - DIALOG_BORDER_WIDTH * 2, DIALOG_HEIGHT - DIALOG_BORDER_WIDTH * 2);

        getContentPane().setLayout(null);
        getContentPane().add(innerPanel);

        setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        setResizable(false);
        getRootPane().setDefaultButton(okButton);
        getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "CANCEL");
        getRootPane().getActionMap().put("CANCEL", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
                result = false;
            }
        });
        //getRootPane().set
        setModal(true);
    }

    private ActionListener okActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            close();
            result = true;
        }
    };

    private ActionListener cancelActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            close();
            result = false;
        }
    };

    private void close() {
        setVisible(false);
        angle = (Double) angleSpinner.getValue();
        scale = (Double) scaleSpinner.getValue();
    }

    public boolean showDialog() {
        setVisible(true);
        return result;
    }

    public double getScale() {
        return scale;
    }

    public double getAngle() {
        return angle;
    }
}
