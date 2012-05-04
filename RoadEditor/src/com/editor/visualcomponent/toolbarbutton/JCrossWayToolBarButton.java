package com.editor.visualcomponent.toolbarbutton;

import com.editor.res.Properties;
import com.editor.visualcomponent.JGridPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Date: 04.05.12
 * Time: 10:26
 *
 * @author: Alexey
 */
public class JCrossWayToolBarButton extends JToolBarButton {

    private final static Icon ICON = Properties.getIcon(Properties.Settings.JCW_TOOLBAR_BUTTON_ICO);

    public JCrossWayToolBarButton(JGridPanel workAria) {
        super(ICON, workAria);
    }

    @Override
    public void actionPerformed() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void actionPerformed(Point point) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
