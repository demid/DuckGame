package com.editor.res;

import org.apache.log4j.Logger;

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
    }

    public interface Settings {
        public static final String MAIN_SCREEN_STATE = "MAIN_SCREEN_STATE";
        public static final String MAIN_SCREEN_WIDTH = "MAIN_SCREEN_WIDTH";
        public static final String MAIN_SCREEN_HEIGHT = "MAIN_SCREEN_HEIGHT";
        public static final String MAIN_SCREEN_MIN_WIDTH = "MAIN_SCREEN_MIN_WIDTH";
        public static final String MAIN_SCREEN_MIN_HEIGHT = "MAIN_SCREEN_MIN_HEIGHT";
        public static final String MAIN_SCREEN_CELL_SIZE = "MAIN_SCREEN_CELL_SIZE";
        public static final String MAIN_SCREEN_CELL_BORDER_WIDTH = "MAIN_SCREEN_CELL_BORDER_WIDTH";
        public static final String EDIT_SCREEN_SATE = "EDIT_SCREEN_SATE";
        public static final String EDIT_SCREEN_WIDTH = "EDIT_SCREEN_WIDTH";
        public static final String EDIT_SCREEN_HEIGHT = "EDIT_SCREEN_HEIGHT";
        public static final String EDIT_SCREEN_MIN_WIDTH = "EDIT_SCREEN_MIN_WIDTH";
        public static final String EDIT_SCREEN_MIN_HEIGHT = "EDIT_SCREEN_MIN_HEIGHT";
        public static final String EDIT_SCREEN_CELL_SIZE = "EDIT_SCREEN_CELL_SIZE";
        public static final String EDIT_SCREEN_CELL_BORDER_WIDTH = "EDIT_SCREEN_CELL_BORDER_WIDTH";
        public static final String EDIT_SCREEN_SLIDER_MAJOR_TRICK_SPACING = "EDIT_SCREEN_SLIDER_MAJOR_TRICK_SPACING";
        public static final String EDIT_SCREEN_SLIDER_MINOR_TRICK_SPACING = "EDIT_SCREEN_SLIDER_MINOR_TRICK_SPACING";
        public static final String EDIT_SCREEN_SLIDER_MAX_VALUE = "EDIT_SCREEN_SLIDER_MAX_VALUE";

    }

    public static String getLabel(String name) {
        LOGGER.trace("Trying to get label : " + name);
        return LABELS.getString(name);
    }

    public static int getInt(String name) {
        LOGGER.trace("Trying to get int : " + name);
        return Integer.parseInt(PROPERTIES.getProperty(name));
    }
}
