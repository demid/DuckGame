package com.editor;

import com.editor.map.Map;
import com.editor.res.Properties;
import com.editor.screen.JMapEditScreen;
import com.editor.screen.dialog.JCrossWayDialog;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.swing.*;

/**
 * Date: 23.04.12
 * Time: 21:34
 *
 * @author: Alexey
 */
public class App {
    private final static Logger LOGGER = Logger.getLogger(App.class);

    public static void main(String[] args) {
        PropertyConfigurator.configure("src/log4j.properties");
        LOGGER.info("Program has been started. Current dir is " + System.getProperty("user.dir"));
        try {
            UIManager.setLookAndFeel(Properties.getString(Properties.Settings.LOOK_AND_FEEL_CLASS));
            LOGGER.info("Current Look and Feel : " + Properties.getString(Properties.Settings.LOOK_AND_FEEL_CLASS));
        } catch (ClassNotFoundException e) {
            LOGGER.error(e);
        } catch (InstantiationException e) {
            LOGGER.error(e);
        } catch (IllegalAccessException e) {
            LOGGER.error(e);
        } catch (UnsupportedLookAndFeelException e) {
            LOGGER.error(e);
        }

      JMapEditScreen editScreenJ = new JMapEditScreen();
      editScreenJ.setVisible(true);
      editScreenJ.loadMap(new Map(600, 600));

    }

}
