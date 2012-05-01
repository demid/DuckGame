package com.editor.screen.dialog;

import com.editor.res.Properties;
import sun.awt.HorizBagLayout;
import sun.awt.VerticalBagLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Date: 30.04.12
 * Time: 14:38
 *
 * @author: Alexey
 */
public class JCrossWayDialog extends JDialog {
    private JSpinner angleSpinner;
    private JSpinner scaleSpinner;
    private double scale;
    private double angle;
    private boolean result = false;

    public JCrossWayDialog(String title,double angle, double scale) {
        setTitle(title);
        JPanel innerPanel = new JPanel(new VerticalBagLayout());
        angleSpinner = new JSpinner(new SpinnerNumberModel(angle, -Double.MAX_VALUE, Double.MAX_VALUE, Properties.getDouble(Properties.Settings.DIALOG_SPINNER_STEP)));
        scaleSpinner = new JSpinner(new SpinnerNumberModel(scale, 0, Double.MAX_VALUE, Properties.getDouble(Properties.Settings.DIALOG_SPINNER_STEP)));
        JLabel angleLabel = new JLabel(Properties.getLabel(Properties.Labels.ANGLE_LABEL));
        angleLabel.setPreferredSize(new Dimension(Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_WIDTH),
                Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_HEIGHT)));
        innerPanel.add(angleLabel);
        innerPanel.add(angleSpinner);
        JLabel scaleLabel = new JLabel(Properties.getLabel(Properties.Labels.SCALE_LABEL));
        scaleLabel.setPreferredSize(new Dimension(Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_WIDTH),
                Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_HEIGHT)));
        innerPanel.add(scaleLabel);
        innerPanel.add(scaleSpinner);
        JButton okButton = new JButton(Properties.getLabel(Properties.Labels.OK_BUTTON_LABEL));
        okButton.setPreferredSize(new Dimension(Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_WIDTH),
                Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_HEIGHT)));
        okButton.addActionListener(okActionListener);

        JButton cancelButton = new JButton(Properties.getLabel(Properties.Labels.CANCEL_BUTTON_LABEL));
        cancelButton.setPreferredSize(new Dimension(Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_WIDTH),
                Properties.getInt(Properties.Settings.EDIT_SCREEN_SPINNER_HEIGHT)));
        cancelButton.addActionListener(cancelActionListener);

        
        JPanel panel = new JPanel(new HorizBagLayout());
        panel.add(okButton);
        panel.add(cancelButton);
        innerPanel.add(panel);

        add(innerPanel);
        //add(scaleSpinner);
        setSize(Properties.getInt(Properties.Settings.DIALOG_WIDTH), Properties.getInt(Properties.Settings.DIALOG_HEIGHT));
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
