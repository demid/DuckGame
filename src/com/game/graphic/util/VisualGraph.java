package com.game.graphic.util;

import com.game.util.CrossWayCoordinate;
import com.game.util.WayEdge;
import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.Set;

/**
 * Date: 19.04.12
 * Time: 16:32
 *
 * @author: Alexey
 */
//TODO Add builder
public class VisualGraph extends Scene {
    private static final Log LOG = LogFactory.getLog(VisualGraph.class);
    public static final int DEFAULT_VERTEX_WIDTH = 20;
    public static final int DEFAULT_LINE_WIDTH = 2;
    public static final int DEFAULT_ARROW_WIDTH = 10;

    public static final float DEFAULT_LINE_R = 1;
    public static final float DEFAULT_LINE_G = 1;
    public static final float DEFAULT_LINE_B = 1;

    public static final float DEFAULT_VERTEX_R = 1;
    public static final float DEFAULT_VERTEX_G = 1;
    public static final float DEFAULT_VERTEX_B = 1;


    public static final float DEFAULT_ARROW_R = 0;
    public static final float DEFAULT_ARROW_G = 1;
    public static final float DEFAULT_ARROW_B = 0;


    public VisualGraph(Graph<CrossWayCoordinate,WayEdge> roadGraph, int vertexSize, int lineWidth, int arrowSize, float lineR, float lineG, float lineB, float vertexR, float vertexG, float vertexB, float arrowR, float arrowG, float arrowB) {


        if (roadGraph == null) {
            throw new IllegalArgumentException(new NullPointerException("'roadGraph' can't be null."));
        }

        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
        Set<WayEdge> wayEdgeSet = roadGraph.edgeSet();
        for (WayEdge wayEdge : wayEdgeSet) {
            CrossWayCoordinate source = roadGraph.getEdgeSource(wayEdge);
            CrossWayCoordinate target = roadGraph.getEdgeTarget(wayEdge);
            Line line = new Line((float) source.getX(), (float) source.getY(), (float) target.getX(), (float) target.getY(), lineWidth);
            line.setColor(lineR, lineG, lineB);
            attachChild(line);

            double lineLenght = (float) Math.sqrt(Math.pow(target.getX() - source.getX(), 2) + Math.pow(target.getY() - source.getY(), 2));
            double k = (target.getX() - source.getX()) / lineLenght;
            double p = (target.getY() - source.getY()) / lineLenght;

            Rectangle rectangleS = new Rectangle((float) (target.getX() - k * (vertexSize / 2) - arrowSize / 2), (float) (target.getY() - p * (vertexSize / 2) - arrowSize / 2), arrowSize, arrowSize);
            rectangleS.setColor(arrowR, arrowG, arrowB);
            //attachChild(rectangleS);
            rectangles.add(rectangleS);

        }

        Set<CrossWayCoordinate> geographicCrossWays = roadGraph.vertexSet();
        for (CrossWayCoordinate crossWay : geographicCrossWays) {
            Rectangle rectangleS = new Rectangle((float) (crossWay.getX() - vertexSize / 2), (float) (crossWay.getY() - vertexSize / 2), vertexSize, vertexSize);
            rectangleS.setColor(vertexR, vertexG, vertexB);
            attachChild(rectangleS);
        }
        for (Rectangle rectangle : rectangles) {
            attachChild(rectangle);
        }
    }

    public VisualGraph(Graph<CrossWayCoordinate,WayEdge> roadGraph) {
        this(roadGraph, DEFAULT_VERTEX_WIDTH, DEFAULT_LINE_WIDTH, DEFAULT_ARROW_WIDTH, DEFAULT_LINE_R, DEFAULT_LINE_G, DEFAULT_LINE_B, DEFAULT_VERTEX_R, DEFAULT_VERTEX_G, DEFAULT_VERTEX_B, DEFAULT_ARROW_R, DEFAULT_ARROW_G, DEFAULT_ARROW_B);
        LOG.warn("You are using  constructor for creating VisualGraph with default parameters. It is recommended to use flexible constructor and some properties file.");
    }
}
