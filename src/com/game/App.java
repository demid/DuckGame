package com.game;

import com.game.graphic.util.VisualGraph;
import com.game.roadnetwork.Direction;
import com.game.roadnetwork.GeographicCrossWay;
import com.game.roadnetwork.Road;
import com.game.roadnetwork.Way;
import com.game.util.GraphFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.util.FPSLogger;



public class App extends BaseExample {
    private static final int CAMERA_WIDTH = (int) (720 );
    private static final int CAMERA_HEIGHT = (int) (480);
    private Camera mCamera;

    @Override
    public Engine onLoadEngine() {
        this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
    }

    @Override
    public void onLoadResources() {

    }

    @Override
    public Scene onLoadScene() {
        this.mEngine.registerUpdateHandler(new FPSLogger());
        final Scene scene = new Scene();
        scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f));
        double scale = 100;

        Road r1 = new Road(new Way(Direction.FORWARD),new Way(Direction.FORWARD),new Way(Direction.FORWARD));
        Road r2 = new Road(new Way(Direction.FORWARD),new Way(Direction.FORWARD));
        Road r3 = new Road(new Way(Direction.FORWARD),new Way(Direction.FORWARD));
        Road r4 = new Road(new Way(Direction.FORWARD),new Way(Direction.FORWARD));


        GeographicCrossWay c1 = new GeographicCrossWay(1, -200, 0, Math.PI/2, scale);
        c1.attachRoad(0, r1, true);

        GeographicCrossWay c2 = new GeographicCrossWay(1, 0, +200, 0, scale);
        c2.attachRoad(0, r2, false);

        GeographicCrossWay c3 = new GeographicCrossWay(1, 300, 50, 3*Math.PI/2, scale);
        c3.attachRoad(0, r3, false);

        GeographicCrossWay c4 = new GeographicCrossWay(1, 0, -200, Math.PI, scale);
        c4.attachRoad(0, r4, true);

        GeographicCrossWay c = new GeographicCrossWay(4, 0, 0, -Math.PI/5, scale);
        c.attachRoad(2, r1, false);
        c.attachRoad(1, r2, true);
        c.attachRoad(0, r3, true);
        c.attachRoad(3, r4, false);


        VisualGraph visualGraph = new VisualGraph(GraphFactory.createGraphForRoad(c1, c2, c3,c4,c));

        /*
        GeographicCrossWay c = new GeographicCrossWay(8, 0, 0,0, scale);
        Road r1 = new Road(new Way(Direction.FORWARD),new Way(Direction.BACK));
        Road r2 = new Road(new Way(Direction.FORWARD),new Way(Direction.FORWARD));
        Road r3 = new Road(new Way(Direction.BACK),new Way(Direction.FORWARD));
        Road r4 = new Road(new Way(Direction.FORWARD),new Way(Direction.FORWARD));

        c.attachRoad(0,r1,true);
        c.attachRoad(2,r2,true);
        c.attachRoad(4,r3,false);
        c.attachRoad(5,r4,false);

        VisualGraph visualGraph = new VisualGraph(GraphFactory.createGraphForRoad(c));
         */
        visualGraph.setPosition(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2);
        scene.attachChild(visualGraph);

        return scene;
    }

    @Override
    public void onLoadComplete() {

    }

}