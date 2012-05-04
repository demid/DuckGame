package com.editor.screen;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * Date: 01.05.12
 * Time: 19:00
 *
 * @author: Alexey
 */
public interface WorkComponent {

    public void setComponentContainer(ComponentContainer componentContainer);

    public JComponent getComponent();

    public boolean doubleClicked(MouseEvent e);

    public boolean mouseClicked(MouseEvent e);

    public boolean mousePressed(MouseEvent e);

    public boolean mouseReleased(MouseEvent e);

    public boolean mouseEntered(MouseEvent e);

    public boolean mouseExited(MouseEvent e);

    public boolean mouseDragged(MouseEvent e);

    public boolean mouseMoved(MouseEvent e);
}
