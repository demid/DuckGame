package com.editor.screen;

import com.editor.res.Properties;
import com.editor.visualcomponent.JGridPanel;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Date: 23.04.12
 * Time: 21:46
 *
 * @author: Alexey
 */
public class MainScreen extends JFrame {
    private final static Logger LOGGER = Logger.getLogger(MainScreen.class);
    private JPanel toolBar = new JPanel();
    private JGridPanel workAria  = new JGridPanel();
    public MainScreen() {
        super(Properties.getLabel(Properties.Labels.MAIN_SCREEN_LABEL));
        setExtendedState(Properties.getInt(Properties.Settings.MAIN_SCREEN_STATE));
        setSize(Properties.getInt(Properties.Settings.MAIN_SCREEN_WIDTH), Properties.getInt(Properties.Settings.MAIN_SCREEN_HEIGHT));
        setMinimumSize(new Dimension(Properties.getInt(Properties.Settings.MAIN_SCREEN_MIN_WIDTH),
                Properties.getInt(Properties.Settings.MAIN_SCREEN_MIN_HEIGHT)));
        addWindowListener(new BasicWindowMonitor());
        //WorkArial initialization
        workAria.add(new JButton("Button1"));
        workAria.setBorder(new LineBorder(Color.BLACK));
        workAria.setCellSize(Properties.getInt(Properties.Settings.MAIN_SCREEN_CELL_SIZE));
        workAria.setBorderCellWidth(Properties.getInt(Properties.Settings.MAIN_SCREEN_CELL_BORDER_WIDTH));
        //ToolBar initialization
        toolBar.add(new JButton("Button2"));
        toolBar.setBorder(new LineBorder(Color.BLACK));


        add(workAria,BorderLayout.CENTER);
        add(toolBar,BorderLayout.EAST);

        LOGGER.trace("Screen has been created.");
    }


}
