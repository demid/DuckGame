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
    private int dialogWidth = Properties.getInt(Properties.Settings.JCW_DIALOG_WIDTH);
    private int dialogBorderWidth = Properties.getInt(Properties.Settings.JCW_DIALOG_BORDER_WIDTH);

    private int dialogHeight = Properties.getInt(Properties.Settings.JCW_DIALOG_HEIGHT);
    private double dialogSpinnerStep = Properties.getDouble(Properties.Settings.JCW_DIALOG_SPINNER_STEP);
    private int dialogComponentWidth = Properties.getInt(Properties.Settings.JCW_DIALOG_COMPONENT_WIDTH);
    private int dialogComponentHeight = Properties.getInt(Properties.Settings.JCW_DIALOG_COMPONENT_HEIGHT);


    private String dialogAngleLabel = Properties.getLabel(Properties.Labels.JCW_DIALOG_ANGLE_LABEL);
    private String dialogScaleLabel = Properties.getLabel(Properties.Labels.JCW_DIALOG_SCALE_LABEL);
    private String okButtonLabel = Properties.getLabel(Properties.Labels.OK_BUTTON_LABEL);
    private String cancelButtonLabel = Properties.getLabel(Properties.Labels.CANCEL_BUTTON_LABEL);


    private JSpinner angleSpinner;
    private JSpinner scaleSpinner;
    private double scale;
    private double angle;
    private boolean result = false;


    public JCrossWayDialog(String title, double angle, double scale) {
        setTitle(title);
        JPanel innerPanel = new JPanel(new VerticalBagLayout());
        angleSpinner = new JSpinner(new SpinnerNumberModel(angle, -Double.MAX_VALUE, Double.MAX_VALUE, dialogSpinnerStep));
        scaleSpinner = new JSpinner(new SpinnerNumberModel(scale, 0, Double.MAX_VALUE, dialogSpinnerStep));
        JLabel angleLabel = new JLabel(dialogAngleLabel);
        angleLabel.setPreferredSize(new Dimension(dialogComponentWidth,dialogComponentHeight));
        innerPanel.add(angleLabel);
        innerPanel.add(angleSpinner);
        JLabel scaleLabel = new JLabel(dialogScaleLabel);
        scaleLabel.setPreferredSize(new Dimension(dialogComponentWidth,dialogComponentHeight));
        innerPanel.add(scaleLabel);
        innerPanel.add(scaleSpinner);
        JButton okButton = new JButton(okButtonLabel);
        okButton.setPreferredSize(new Dimension(dialogComponentWidth,dialogComponentHeight));
        okButton.addActionListener(okActionListener);

        JButton cancelButton = new JButton(cancelButtonLabel);
        cancelButton.setPreferredSize(new Dimension(dialogComponentWidth,dialogComponentHeight));
        cancelButton.addActionListener(cancelActionListener);


        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(okButton);
        panel.add(cancelButton);
        innerPanel.add(panel);


        innerPanel.setBounds(dialogBorderWidth, dialogBorderWidth, dialogWidth - dialogBorderWidth*2,dialogHeight - dialogBorderWidth*2);

        getContentPane().setLayout(null);
        getContentPane().add(innerPanel);

        setSize(dialogWidth, dialogHeight);
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
