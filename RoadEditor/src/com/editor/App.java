package com.editor;

import com.editor.map.Map;
import com.editor.screen.MainScreen;
import com.editor.screen.MapEditScreen;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

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
        LOGGER.info("Program has been started.");
        //new MainScreen().setVisible(true);
        MapEditScreen editScreen = new MapEditScreen();
        editScreen.setVisible(true);
        editScreen.loadMap(new Map(300,300));

    }

}
