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
        //Common labels
        public static final String OK_BUTTON_LABEL = "OK_BUTTON_LABEL";
        public static final String CANCEL_BUTTON_LABEL = "CANCEL_BUTTON_LABEL";

        //com.editor.screen.JMapEditScreen
        public static final String JMES_WIDTH_SLIDER_LABEL = "JMES_WIDTH_SLIDER_LABEL";
        public static final String JMES_HEIGHT_SLIDER_LABEL = "JMES_HEIGHT_SLIDER_LABEL";
        public static final String JMES_DRAW_GRID_LABEL = "JMES_DRAW_GRID_LABEL";
        public static final String JMES_GRID_SIZE_LABEL = "JMES_GRID_SIZE_LABEL";
        public static final String JMES_WORK_ARIA_SETTINGS_LABEL = "JMES_WORK_ARIA_SETTINGS_LABEL";
        public static final String JMES_WORK_ARIA_MAP_OBJECTS_LABEL = "JMES_WORK_ARIA_MAP_OBJECTS_LABEL";

        //com.editor.screen.dialog.JCrossWayDialog
        public static final String JCW_DIALOG_SCALE_LABEL = "JCW_DIALOG_SCALE_LABEL";
        public static final String JCW_DIALOG_ANGLE_LABEL = "JCW_DIALOG_ANGLE_LABEL";


        //com.editor.screen.dialog.JCrossWayCreateDialog
        public static final String JCW_CREATE_DIALOG_ROAD_NUMBER_LABEL = "JCW_CREATE_DIALOG_ROAD_NUMBER_LABEL";
        public static final String JCW_CREATE_DIALOG_POSITION_LABEL = "JCW_CREATE_DIALOG_POSITION_LABEL";
        public static final String JCW_CREATE_DIALOG_SCALE_LABEL = "JCW_CREATE_DIALOG_SCALE_LABEL";
        public static final String JCW_CREATE_DIALOG_ANGLE_LABEL = "JCW_CREATE_DIALOG_ANGLE_LABEL";

    }

    public interface Settings {
        //====== Global visual settings
        public static final String LOOK_AND_FEEL_CLASS = "LOOK_AND_FEEL_CLASS";

        //com.editor.screen.JMapEditScreen
        public static final String JMES_STARTUP_SATE = "JMES_STARTUP_SATE";
        public static final String JMES_WIDTH = "JMES_WIDTH";
        public static final String JMES_HEIGHT = "JMES_HEIGHT";
        public static final String JMES_MIN_WIDTH = "JMES_MIN_WIDTH";
        public static final String JMES_MIN_HEIGHT = "JMES_MIN_HEIGHT";
        public static final String JMES_CELL_SIZE = "JMES_CELL_SIZE";
        public static final String JMES_CELL_BORDER_WIDTH = "JMES_CELL_BORDER_WIDTH";
        public static final String JMES_COMPONENT_WIDTH = "JMES_COMPONENT_WIDTH";
        public static final String JMES_COMPONENT_HEIGHT = "JMES_COMPONENT_HEIGHT";
        public static final String JMES_SPINNER_STEP = "JMES_SPINNER_STEP";
        public static final String JMES_DRAW_GREED = "JMES_DRAW_GREED";
        public static final String JMES_MAP_OBJECT_BUTTON_HEIGHT = "JMES_MAP_OBJECT_BUTTON_HEIGHT";
        public static final String JMES_MAP_OBJECT_BUTTON_WIDTH = "JMES_MAP_OBJECT_BUTTON_WIDTH";


        //com.editor.visualcomponent.JCrossWay
        public static final String JCW_COMPONENT_WAY_CONNECTOR_SIZE = "JCW_COMPONENT_WAY_CONNECTOR_SIZE";
        public static final String JCW_COMPONENT_SELECTION_POLYGON_WIDTH = "JCW_COMPONENT_SELECTION_POLYGON_WIDTH";

        //com.editor.visualcomponent.JRoad
        public static final String JR_COMPONENT_SELECTION_POLYGON_WIDTH = "JR_COMPONENT_SELECTION_POLYGON_WIDTH";

        //com.editor.visualcomponent.toolbarbutton.JCrossWayToolBarButton
        public static final String JCW_TOOLBAR_BUTTON_ICO = "JCW_TOOLBAR_BUTTON_ICO";


        //com.editor.screen.dialog.JCrossWayDialog
        public static final String JCW_DIALOG_SPINNER_STEP = "JCW_DIALOG_SPINNER_STEP";
        public static final String JCW_DIALOG_BORDER_WIDTH = "JCW_DIALOG_BORDER_WIDTH";
        public static final String JCW_DIALOG_WIDTH = "JCW_DIALOG_WIDTH";
        public static final String JCW_DIALOG_HEIGHT = "JCW_DIALOG_HEIGHT";
        public static final String JCW_DIALOG_COMPONENT_WIDTH = "JCW_DIALOG_COMPONENT_WIDTH";
        public static final String JCW_DIALOG_COMPONENT_HEIGHT = "JCW_DIALOG_COMPONENT_HEIGHT";

        //com.editor.screen.dialog.JCrossWayCreateDialog
        public static final String JCW_CREATE_DIALOG_SPINNER_STEP = "JCW_CREATE_DIALOG_SPINNER_STEP";
        public static final String JCW_CREATE_DIALOG_BORDER_WIDTH = "JCW_CREATE_DIALOG_BORDER_WIDTH";
        public static final String JCW_CREATE_DIALOG_WIDTH = "JCW_CREATE_DIALOG_WIDTH";
        public static final String JCW_CREATE_DIALOG_HEIGHT = "JCW_CREATE_DIALOG_HEIGHT";
        public static final String JCW_CREATE_DIALOG_COMPONENT_WIDTH = "JCW_CREATE_DIALOG_COMPONENT_WIDTH";
        public static final String JCW_CREATE_DIALOG_COMPONENT_HEIGHT = "JCW_CREATE_DIALOG_COMPONENT_HEIGHT";

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
