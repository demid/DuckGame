package com.editor.screen;

import javax.swing.*;

/**
 * Date: 02.05.12
 * Time: 12:09
 *
 * @author: Alexey
 */
public interface ComponentContainer {
    public void addToSelected(JComponent jComponent);
    public void removeFromSelected(JComponent jComponent);

}
