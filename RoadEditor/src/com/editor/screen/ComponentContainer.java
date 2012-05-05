package com.editor.screen;

import com.editor.visualcomponent.WorkComponent;

import java.awt.event.InputEvent;

/**
 * Date: 02.05.12
 * Time: 12:09
 *
 * @author: Alexey
 */
public interface ComponentContainer {
    public void addToSelected(WorkComponent workComponent, InputEvent e);

    public void deleteComponent(WorkComponent workComponent);
}
