package com.editor.visualcomponent.toolbarbutton;

import com.editor.res.Properties;
import com.editor.screen.dialog.JCrossWayCreateDialog;
import com.editor.visualcomponent.JCrossWay;
import com.editor.visualcomponent.JGridPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Date: 04.05.12
 * Time: 10:26
 *
 * @author: Alexey
 */
//TODO add to properties file
public class JCrossWayToolBarButton extends JToolBarButton {

    private final static Icon ICON = Properties.getIcon(Properties.Settings.JCW_TOOLBAR_BUTTON_ICO);

    public JCrossWayToolBarButton(JGridPanel workAria) {
        super(ICON, workAria);
    }

    @Override
    public void actionPerformed() {
        JCrossWay jCrossWay = JCrossWayCreateDialog.showDialog("", getWorkAria().getWidth() / 2, getWorkAria().getHeight() / 2, 4, 0, 50);
        if (jCrossWay != null) {
            getWorkAria().add(jCrossWay);
            getWorkAria().repaint();
        }
    }

    @Override
    public void actionPerformed(Point point) {
        JCrossWay jCrossWay = JCrossWayCreateDialog.showDialog("", 4, 0, 50);
        if (jCrossWay != null) {
            jCrossWay.setLocation(point);
            getWorkAria().add(jCrossWay);
            getWorkAria().repaint();
        }
    }
}
