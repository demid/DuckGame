package com.editor.res;

import com.editor.res.exception.IncorrectSettingException;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Date: 23.04.12
 * Time: 22:54
 *
 * @author: Alexey
 */
public class Properties {
    private final static Logger LOGGER = Logger.getLogger(Properties.class);
    private final static ResourceBundle LABELS = ResourceBundle.getBundle("labels");
    private final static java.util.Properties PROPERTIES = new java.util.Properties();

    static {
        try {
            FileInputStream inputStream = new FileInputStream("src/settings.properties");
            PROPERTIES.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            LOGGER.error(e);
        }
        LOGGER.info("Properties has been initialized.");
    }

    public interface Labels {
        public static final String MAIN_SCREEN_LABEL = "MAIN_SCREEN_LABEL";
        public static final String WIDTH_SLIDER_TITLE = "WIDTH_SLIDER_TITLE";
        public static final String HEIGHT_SLIDER_TITLE = "HEIGHT_SLIDER_TITLE";
        public static final String DRAW_GRID_TITLE = "DRAW_GRID_TITLE";
        public static final String GRID_SIZE_TITLE = "GRID_SIZE_TITLE";
        public static final String WORK_ARIA_SETTINGS_TITLE = "WORK_ARIA_SETTINGS_TITLE";
        public static final String WORK_ARIA_MAP_OBJECTS_TITLE = "WORK_ARIA_MAP_OBJECTS_TITLE";
        public static final String SCALE_LABEL = "SCALE_LABEL";
        public static final String ANGLE_LABEL = "ANGLE_LABEL";
        public static final String OK_BUTTON_LABEL="OK_BUTTON_LABEL";
        public static final String CANCEL_BUTTON_LABEL="CANCEL_BUTTON_LABEL";
    }

    public interface Settings {
        //====== Global visual settings
        public static final String LOOK_AND_FEEL_CLASS = "LOOK_AND_FEEL_CLASS";
        //====== Main screen
        public static final String MAIN_SCREEN_STATE = "MAIN_SCREEN_STATE";
        public static final String MAIN_SCREEN_WIDTH = "MAIN_SCREEN_WIDTH";
        public static final String MAIN_SCREEN_HEIGHT = "MAIN_SCREEN_HEIGHT";
        public static final String MAIN_SCREEN_MIN_WIDTH = "MAIN_SCREEN_MIN_WIDTH";
        public static final String MAIN_SCREEN_MIN_HEIGHT = "MAIN_SCREEN_MIN_HEIGHT";
        public static final String MAIN_SCREEN_CELL_SIZE = "MAIN_SCREEN_CELL_SIZE";
        public static final String MAIN_SCREEN_CELL_BORDER_WIDTH = "MAIN_SCREEN_CELL_BORDER_WIDTH";
        //====== Edit screen
        public static final String EDIT_SCREEN_SATE = "EDIT_SCREEN_SATE";
        public static final String EDIT_SCREEN_WIDTH = "EDIT_SCREEN_WIDTH";
        public static final String EDIT_SCREEN_HEIGHT = "EDIT_SCREEN_HEIGHT";
        public static final String EDIT_SCREEN_MIN_WIDTH = "EDIT_SCREEN_MIN_WIDTH";
        public static final String EDIT_SCREEN_MIN_HEIGHT = "EDIT_SCREEN_MIN_HEIGHT";
        public static final String EDIT_SCREEN_CELL_SIZE = "EDIT_SCREEN_CELL_SIZE";
        public static final String EDIT_SCREEN_CELL_BORDER_WIDTH = "EDIT_SCREEN_CELL_BORDER_WIDTH";
        public static final String EDIT_SCREEN_SPINNER_WIDTH = "EDIT_SCREEN_SPINNER_WIDTH";
        public static final String EDIT_SCREEN_SPINNER_HEIGHT = "EDIT_SCREEN_SPINNER_HEIGHT";
        public static final String EDIT_SCREEN_SPINNER_STEP = "EDIT_SCREEN_SPINNER_STEP";
        public static final String EDIT_SCREEN_DRAW_GREED = "EDIT_SCREEN_DRAW_GREED";


        public static final String MAP_OBJECT_BUTTON_HEIGHT = "MAP_OBJECT_BUTTON_HEIGHT";
        public static final String MAP_OBJECT_BUTTON_WIDTH = "MAP_OBJECT_BUTTON_WIDTH";
        //====== Icons
        public static final String CROSS_WAY_BUTTON_ICO = "CROSS_WAY_BUTTON_ICO";

        //====== Visual setting
        public static final String WAY_LINE_WIDTH = "WAY_LINE_WIDTH";
        public static final String WAY_ARROW_WIDTH = "WAY_ARROW_WIDTH";
        public static final String WAY_ARROW_HEIGHT = "WAY_ARROW_HEIGHT";
        public static final String WAY_ARROW_COLOR = "WAY_ARROW_COLOR";
        public static final String WAY_ARROW_ACTIVE_COLOR = "WAY_ARROW_ACTIVE_COLOR";
        public static final String WAY_CONNECTOR_SIZE = "WAY_CONNECTOR_SIZE";
        public static final String SELECTION_POLYGON_WIDTH = "SELECTION_POLYGON_WIDTH";
        //====== Dialog settings
        public static final String DIALOG_SPINNER_STEP ="DIALOG_SPINNER_STEP";
        public static final String DIALOG_WIDTH = "DIALOG_WIDTH";
        public static final String DIALOG_HEIGHT = "DIALOG_HEIGHT";

    }

    public static Color getColor(String name) {
        LOGGER.trace("Trying to get color : " + name);
        String[] strings = PROPERTIES.getProperty(name).split(" ");
        if (strings.length != 3) {
            throw new IncorrectSettingException("Can't get color for " + name + ". Wrong color format.");
        }
        try {
            return new Color(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2]));
        } catch (RuntimeException e) {
            throw new IncorrectSettingException("Can't get color for " + name, e);
        }
    }

    public static Icon getIcon(String name) {
        try {

            LOGGER.trace("Trying to get icon : " + name);
            ImageIcon icon = new ImageIcon(PROPERTIES.getProperty(name));
            return icon;
        } catch (RuntimeException e) {
            throw new IncorrectSettingException("Can't get icon for " + name, e);
        }
    }

    public static String getLabel(String name) {

        LOGGER.trace("Trying to get label : " + name);
        return LABELS.getString(name);


    }

    public static String getString(String name) {
        LOGGER.trace("Trying to get string : " + name);
        return PROPERTIES.getProperty(name);
    }

    public static int getInt(String name) {
        try {
            LOGGER.trace("Trying to get int : " + name);
            return Integer.parseInt(PROPERTIES.getProperty(name));
        } catch (RuntimeException e) {
            throw new IncorrectSettingException("Can't get int for " + name, e);
        }
    }

    public static double getDouble(String name) {
        try {
            LOGGER.trace("Trying to get double : " + name);
            return Double.parseDouble(PROPERTIES.getProperty(name));
        } catch (RuntimeException e) {
            throw new IncorrectSettingException("Can't get double for " + name, e);
        }
    }


    public static boolean getBoolean(String name) {
        try {
            LOGGER.trace("Trying to get boolean : " + name);
            return Boolean.parseBoolean(PROPERTIES.getProperty(name));
        } catch (RuntimeException e) {
            throw new IncorrectSettingException("Can't get boolean for " + name, e);
        }
    }
}
