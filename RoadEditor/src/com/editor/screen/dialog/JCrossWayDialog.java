package com.editor.screen.dialog;

import com.editor.res.Properties;
import sun.awt.VerticalBagLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Date: 30.04.12
 * Time: 14:38
 *
 * @author: Alexey
 */
public class JCrossWayDialog extends JDialog {
    private JSpinner angleSpinner;
    private JSpinner scaleSpinner;

    public JCrossWayDialog(double angle, double scale) {
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

        add(innerPanel);
        //add(scaleSpinner);
        setSize(Properties.getInt(Properties.Settings.DIALOG_WIDTH), Properties.getInt(Properties.Settings.DIALOG_HEIGHT));
    }
}
