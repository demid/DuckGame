package com.editor.screen;

import org.apache.log4j.Logger;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Date: 23.04.12
 * Time: 23:22
 *
 * @author: Alexey
 */
public class BasicWindowMonitor extends WindowAdapter {
    private final static Logger LOGGER = Logger.getLogger(BasicWindowMonitor.class);

    @Override
    public void windowClosing(WindowEvent e) {
        Window window = e.getWindow();
        window.setVisible(true);
        window.dispose();
        LOGGER.trace(window.getClass().getName() + " has been closed.");
        LOGGER.info("Program has been finished.");
        System.exit(0);
    }
}
