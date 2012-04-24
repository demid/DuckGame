package com.editor.map;

/**
 * Date: 24.04.12
 * Time: 14:41
 *
 * @author: Alexey
 */
public class MapObject {
    private String name;
    private int x;
    private int y;

    public MapObject(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "MapObject{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
