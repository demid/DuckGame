package com.editor.map;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 24.04.12
 * Time: 14:30
 *
 * @author: Alexey
 */
public class Map {
    private int height;
    private int width;
    private List<MapObject> objects = new ArrayList<MapObject>();


    public Map(int height, int width) {
        if (height < 0) {
            throw new IllegalArgumentException("'height' can't be less than 0.");
        }
        if (width < 0) {
            throw new IllegalArgumentException("'width' can't be less than 0.");
        }
        this.height = height;
        this.width = width;
    }

    public void addObject(MapObject mapObject) {
        if (mapObject == null) {
            throw new IllegalArgumentException(new NullPointerException("'mapObject' can't be null."));
        }
        objects.add(mapObject);
    }

    public MapObject deleteObject(MapObject mapObject) {
        if (objects.remove(mapObject)) {
            return mapObject;
        } else {
            throw new IllegalArgumentException("This 'mapObject' isn't on map");
        }
    }


    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (height < 0) {
            throw new IllegalArgumentException("'height' can't be less than 0.");
        }
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (width < 0) {
            throw new IllegalArgumentException("'width' can't be less than 0.");
        }
        this.width = width;
    }
}
