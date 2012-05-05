package com.editor.visualcomponent;

import com.editor.screen.ComponentContainer;

import javax.swing.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

/**
 * Date: 01.05.12
 * Time: 19:00
 *
 * @author: Alexey
 */
public interface WorkComponent {

    public JComponent getComponent();

    public void setContainer(ComponentContainer container);

    public boolean doubleClicked(MouseEvent e);

    public boolean mouseClicked(MouseEvent e);

    public boolean mousePressed(MouseEvent e);

    public boolean mouseReleased(MouseEvent e);

    public boolean mouseEntered(MouseEvent e);

    public boolean mouseExited(MouseEvent e);

    public boolean mouseDragged(MouseEvent e);

    public boolean mouseMoved(MouseEvent e);

    public boolean canBeSelected(InputEvent event);

    public void setSelected(boolean selected);

    public boolean isSelected();

    public void delete();
}
