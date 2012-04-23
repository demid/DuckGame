package com.game.roadnetwork;

/**
 * Date: 19.04.12
 * Time: 14:32
 *
 * @author: Alexey
 */
public class GeographicCrossWay extends CrossWay {
    private double x;
    private double y;
    private double angle;
    private double scale = 1;

    public GeographicCrossWay(int maxPlaces) {
        super(maxPlaces);
    }

    public GeographicCrossWay(int maxPlaces, double x, double y) {
        super(maxPlaces);
        this.x = x;
        this.y = y;
    }

    public GeographicCrossWay(int maxPlaces, double x, double y, double angle) {
        super(maxPlaces);
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public GeographicCrossWay(int maxPlaces, double x, double y, double angle, double scale) {
        super(maxPlaces);
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.scale = scale;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }


}
